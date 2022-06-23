package com.android.fade.ui.change_location

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.fade.R
import com.android.fade.databinding.ActivityAppointmentsBinding
import com.android.fade.databinding.ActivityChangeLocationBinding
import com.android.fade.ui.enter_address_manually.EnterAddressActivity
import com.android.fade.ui.select_postcode.SelectPostCodeActivity
import com.android.fade.ui.use_current_location.CurrentLocationActivity

class ChangeLocationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChangeLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ivBackButton.setOnClickListener(this)
        binding.relCurrentLocation.setOnClickListener(this)
        binding.relPostCode.setOnClickListener(this)
        binding.relEnterManually.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBackButton -> finish()
            R.id.relCurrentLocation -> goToCurrentLocation()
            R.id.relPostCode -> goToPostCodeScreen()
            R.id.relEnterManually -> goToEnterManuallyScreen()
        }
    }

    private fun goToEnterManuallyScreen() {
        val intent = Intent(this, EnterAddressActivity::class.java)
        startActivity(intent)
    }

    private fun goToPostCodeScreen() {
        val intent = Intent(this, SelectPostCodeActivity::class.java)
        startActivity(intent)
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up)
    }

    private fun goToCurrentLocation() {
        val intent = Intent(this, CurrentLocationActivity::class.java)
        startActivity(intent)
    }
}