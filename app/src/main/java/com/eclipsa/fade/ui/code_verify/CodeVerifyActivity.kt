package com.eclipsa.fade.ui.code_verify

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eclipsa.fade.MainActivity
import com.eclipsa.fade.R
import com.eclipsa.fade.databinding.ActivityCodeVerifyBinding
import com.eclipsa.fade.extension.hideKeyboard
import com.eclipsa.fade.extension.snackBar
import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.network.Resource
import com.eclipsa.fade.ui.login.LoginActivity
import com.eclipsa.fade.ui.profile.ProfileActivity
import com.eclipsa.fade.ui.profile.Result
import com.eclipsa.fade.utils.Constants
import com.eclipsa.fade.utils.Prefrences
import com.eclipsa.fade.utils.Utils
import com.eclipsa.fade.utils.Utils.enable
import com.eclipsa.fade.utils.Utils.hide
import com.eclipsa.fade.utils.Utils.toast
import com.eclipsa.fade.utils.Utils.visible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.gson.Gson
import gun0912.tedimagepicker.util.ToastUtil
import gun0912.tedimagepicker.util.ToastUtil.context
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit


class CodeVerifyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mVerificationId: String
    private val TAG = "CodeVerifyActivity"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityCodeVerifyBinding
    private lateinit var viewModel: CodeVerifyViewModel
    private var mobileNumber: String? = null
    private var selectedCountry: LoginActivity.SelectedCountry? = null
    private var isEmailLogin: Boolean = false
    private var userEmail: String = ""
    private var otp: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            CodeVerifyViewModelFactory(CodeVerifyRepository(MyApi.getInstance()))
        ).get(CodeVerifyViewModel::class.java)
        binding.otpView.requestFocus()

        isEmailLogin = intent.getBooleanExtra(Constants.IS_EMAIL_LOGIN, false)
        if (!isEmailLogin) {
            mobileNumber = intent.getStringExtra(Constants.USER_MOBILE_NO)
            selectedCountry =
                intent.getSerializableExtra(Constants.SelectedCountry) as LoginActivity.SelectedCountry

            Prefrences.savePreferencesString(
                this@CodeVerifyActivity,
                Constants.PREFS_CODE,
                selectedCountry!!.selectedCountryId!!
            )

            Prefrences.savePreferencesString(
                this@CodeVerifyActivity,
                Constants.SELECTED_COUNTRY_CODE,
                selectedCountry!!.selectedCountryCode!!
            )
        } else {
            userEmail = intent.getStringExtra(Constants.EMAIL_ID)!!
            otp = intent.getStringExtra(Constants.OTP)!!
        }

        setUpFirebase()
        if (isEmailLogin) {

        } else {
            sendOtp()
        }

        setOnClickListener()
        binding.otpView.enable(true)
        binding.progressBar.visibility = View.GONE

        viewModel.loginResponse.observe(this) {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (it.value.success!!) {
                        if (it.value.result != null) {
                            if (it.value.result.role_id == 3) {
                                if (it.value.message!!.contains("User Not Found")) {
                                    val intent = Intent(this, ProfileActivity::class.java)
                                    intent.putExtra(Constants.USER_MOBILE_NO, mobileNumber)
                                    intent.putExtra(Constants.IS_FIRST_TIME, "YES")
                                    startActivity(intent)
                                    finish()
                                } else {
                                    savePreferences(it.value.result)
                                    var intent = Intent(this, com.eclipsa.fade.MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                ToastUtil.showToast("Can't register with Barber Mobile Number")
                                finish()
                            }
                        }

                    } else {
                        if (it.value.message!!.contains("User Not Found")) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            intent.putExtra(Constants.USER_MOBILE_NO, mobileNumber)
                            intent.putExtra(Constants.IS_FIRST_TIME, "YES")
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                is Resource.Failure -> {
                    if (it.errorCode == 404) {
                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.putExtra(Constants.USER_MOBILE_NO, mobileNumber)
                        intent.putExtra(Constants.IS_FIRST_TIME, "YES")
                        startActivity(intent)
                        finish()
                    } else {
                        Utils.handleApiError(binding.root, it)
                    }
                }
            }
        }

        viewModel.emailLoginResponse.observe(this) {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (it.value.success!!) {
                        if (it.value.result != null) {
                            savePreferences(it.value.result)
                            var intent = Intent(this, com.eclipsa.fade.MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }

                    } else {
                        ToastUtil.showToast(it.value.message!!)
                    }

                }
                is Resource.Failure -> {
                    Utils.handleApiError(binding.root, it)
                }
            }
        }

    }

    private fun setUpFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.setLanguageCode("En")
        binding.otpView.requestFocus()
    }

    private val mCallbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Log.e(TAG, "onVerificationCompleted:$phoneAuthCredential")
                binding.progressBar.visibility = View.GONE
                binding.otpView.enable(true)
                binding.otpView.requestFocus()
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    binding.otpView.setText(code)
                } else {
                    signInWithPhoneAuthCredential(phoneAuthCredential)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.e(TAG, "SMS sent ${e.message}")
                binding.progressBar.visibility = View.GONE
                Utils.showErrorDialog(this@CodeVerifyActivity, e.message.toString())
            }

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                //super.onCodeSent(s, forceResendingToken);
                Log.e(TAG, "SMS sent $s")
                /* if (isLoading) hide()
                 binding.rlMain.setVisibility(View.VISIBLE)
                 binding.progressBar.setVisibility(View.GONE)*/
                binding.otpView.enable(true)
                binding.otpView.requestFocus()
                binding.progressBar.visibility = View.GONE
                setTimer()
                Utils.showKeyboard(this@CodeVerifyActivity)
                mVerificationId = s
            }
        }

    private fun setTimer() {
        Calendar.getInstance()
        object : CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.tvResendOtp.isEnabled = false
                binding.tvResendOtp.text =
                    "Seconds Remaining: " + getTimeFormat(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                binding.tvResendOtp.isEnabled = true
                binding.tvResendOtp.text = getString(R.string.resend_code)
            }
        }.start()
    }

    private fun getTimeFormat(time: Long): String {
        val s = time.toString() + ""
        if (s.length == 2) return "00:$s"
        return if (s.length == 1) "00:0$s" else ""
    }

    private fun savePreferences(data: Result) {
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.USER_ID,
            data.id.toString()
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.ROLE_ID,
            data.role_id!!.toString()
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.USER_MOBILE_NO,
            data.mobile!!.toString()
        )
        Prefrences.savePreferencesString(this@CodeVerifyActivity, Constants.USER_NAME, data.name!!)
        Prefrences.savePreferencesString(this@CodeVerifyActivity, Constants.API_TOKEN, data.token!!)
        Prefrences.savePreferencesString(this@CodeVerifyActivity, Constants.EMAIL_ID, data.email!!)
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.USER_ADDRESS,
            data.address_line_1!! + " " + data.address_line_2
        )
        Prefrences.savePreferencesString(this@CodeVerifyActivity, Constants.LAT, data.latitude!!)
        Prefrences.savePreferencesString(this@CodeVerifyActivity, Constants.LON, data.longitude!!)
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.LATEST_LAT,
            data.latest_latitude!!
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.LATEST_LON,
            data.latest_longitude!!
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.VAN_NUMBER,
            data.van_number!!
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.USER_GENDER,
            data.gender!!
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.PROFILE_APPROVED,
            data.profile_approved!!.toString()
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.IS_ACTIVE,
            data.is_active!!.toString()
        )
        Prefrences.savePreferencesString(
            this@CodeVerifyActivity,
            Constants.USER_PROFILE_IMAGE,
            Constants.STORAGE_URL.plus(data.profile!!)
        )

        viewModel.profilePic.value = data.profile
        viewModel.userName.value = data.name

        Log.e(TAG, "savePreferences: " + Gson().toJson(data))
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        Log.e(TAG, "signInWithCredential:success")
                        val user = task.result!!.user
                        loginUser()
                    } else {
                        Log.e(TAG, "signInWithCredential:fail.....")
                        Toast.makeText(this, "Otp Verification Failed", Toast.LENGTH_LONG).show()
                        //showInfoDialogCustom(task.exception!!.message)
                    }
                })
    }

    private fun loginUser() {
        viewModel.viewModelScope.launch {
            viewModel.login(
                mobileNumber.toString(),
                selectedCountry!!.selectedCountryCode!!
            )
        }
    }

    private fun sendOtp() {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+${selectedCountry!!.selectedCountryCode!!}$mobileNumber") // Phone number to verify
            .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun setOnClickListener() {
        binding.ivBackButton.setOnClickListener(this)
        binding.tvResendOtp.setOnClickListener(this)

        binding.otpView.setOtpCompletionListener {
            val code = binding.otpView.text.toString().trim()
            verifyVerificationCode(code)
            binding.root.hideKeyboard()
        }
    }

    private fun verifyVerificationCode(code: String) {

        if (isEmailLogin) {

            if(otp == code){
                viewModel.viewModelScope.launch {
                    viewModel.loginUserOtpVerify(userEmail, otp.toInt())
                }
            } else {
                ToastUtil.showToast("OTP Verification Failed!")
            }

        } else {
            if (code == null) return
            if (mVerificationId == null) return
            if (code == "" || mVerificationId == "") return
            //creating the credential
            val credential = PhoneAuthProvider.getCredential(mVerificationId, code)
            //signing the user
            signInWithPhoneAuthCredential(credential)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButton -> finish()
            R.id.tvResendOtp -> resendOtp()
        }
    }

    private fun resendOtp() {
        binding.otpView.setText("")
        setTimer()
        sendOtp()
    }
}