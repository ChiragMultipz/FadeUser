package com.eclipsa.fade.ui.login

import CountryFlags.getCountryFlagByCountryCode
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eclipsa.fade.R
import com.eclipsa.fade.databinding.ActivityLoginBinding
import com.eclipsa.fade.network.MyApi
import com.eclipsa.fade.network.Resource
import com.eclipsa.fade.ui.code_verify.CodeVerifyActivity
import com.eclipsa.fade.utils.Constants
import com.eclipsa.fade.utils.Utils.toast
import com.eclipsa.fade.ui.select_country.SelectCountryActivity
import gun0912.tedimagepicker.util.ToastUtil
import kotlinx.coroutines.launch
import java.io.Serializable

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var countrySpinnerAdapter: CountrySpinnerAdapter
    private var countryId: String? = null
    var countryList: ArrayList<ResultItem?> = ArrayList()
    var selectedCountry: String = "Select Country"
    var selectedCountryID: String = ""
    var isFirstTime: String? = null

    data class SelectedCountry (
        var selectedCountryFlag: String? = "GB",
        var selectedCountryCode: String? = "44",
        var selectedCountryId: String? = "232"
    ) : Serializable

    var selectedCountryData : SelectedCountry= SelectedCountry()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setClickListeners()
        spinnerListener()

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(LoginRepository(MyApi.getInstance()))
        ).get(LoginViewModel::class.java)

        binding.ivCountryFlag.text =
            " ${getCountryFlagByCountryCode(SelectedCountry().selectedCountryFlag!!)}"
        binding.tvCountryCode.text = SelectedCountry().selectedCountryCode!!

        selectedCountryData = SelectedCountry()

        getCountryCode()
        //Country response handler
        viewModel.countryCodeResponse.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    if (it.value.success!!) {
                        countryList.addAll(it.value.result!!)
                        countrySpinnerAdapter = CountrySpinnerAdapter(ToastUtil.context, countryList)
                        var indexOfFirst = 1
                        indexOfFirst = countryList.indexOfFirst { resultItem ->
                            resultItem!!.id.toString() == "232"
                        }
//                        binding.spnCity.adapter = countrySpinnerAdapter
//                        binding.spnCity.setSelection(indexOfFirst)

                    } else {

                    }
                }
                is Resource.Failure -> {
                   // Utils.handleApiError(binding.root, it)
                }
            }
        })
    }

    private fun setClickListeners() {
        binding.relGetStarted.setOnClickListener(this)
        binding.linSelectCountry.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.relGetStarted -> goToVerifyPhoneNumScreen()
            R.id.linSelectCountry -> {
                val intent = Intent(this@LoginActivity, SelectCountryActivity::class.java)
                intent.putExtra(Constants.SelectedCountry,selectedCountryData)
                activityResultLaunch.launch(intent);
            }
        }
    }

    private var activityResultLaunch = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 123) {
            val data: Intent = result.data!!
            selectedCountryData = data.getSerializableExtra(Constants.SelectedCountry) as SelectedCountry
            binding.ivCountryFlag.text = " ${selectedCountryData.selectedCountryFlag}"
            binding.tvCountryCode.text = selectedCountryData.selectedCountryCode
        }
    }

    private fun goToVerifyPhoneNumScreen() {
        if (isValid()) {
            val intent = Intent(this, CodeVerifyActivity::class.java)
            intent.putExtra(Constants.USER_MOBILE_NO, binding.edtPhoneNumber.text.toString())
            intent.putExtra(Constants.SelectedCountry, selectedCountryData)
            intent.putExtra(Constants.IS_EMAIL_LOGIN, false)
            startActivity(intent)
        }

    }

    private fun isValid(): Boolean {
        return when {
            TextUtils.isEmpty(binding.edtPhoneNumber.text.toString()) -> {
                this.toast(getString(R.string.err_empty_phone_number))
                false
            }
            binding.edtPhoneNumber.text.toString().length < 10 -> {
                this.toast(getString(R.string.err_valid_phone_number))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun getCountryCode() {
        viewModel.viewModelScope.launch {
            viewModel.countryCode()
        }
    }

    private fun spinnerListener() {
        /*binding.spnCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing Selected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCountry = countryList[position]!!.phonecode!!
                selectedCountryID = "${countryList[position]!!.id!!}"
                Prefrences.savePreferencesString(this@LoginActivity, Constants.SELECTED_COUNTRY_CODE, countryList[position]!!.phonecode!!)
                Prefrences.savePreferencesString(this@LoginActivity, Constants.PREFS_CODE, "${countryList[position]!!.id!!}")
            }
        }*/
    }
}