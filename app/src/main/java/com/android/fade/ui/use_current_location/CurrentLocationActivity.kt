package com.android.fade.ui.use_current_location

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.fade.MainActivity
import com.android.fade.R
import com.android.fade.databinding.ActivityCurrentLocationBinding
import com.android.fade.utils.Constants
import com.android.fade.utils.ManagePermissions
import com.android.fade.utils.Prefrences
import com.android.fade.utils.Utils
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class CurrentLocationActivity : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {

    lateinit var binding: ActivityCurrentLocationBinding
    private lateinit var mMap: GoogleMap
    val list = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    var mCurrentLat: Double? = null
    var mCurrentLon: Double? = null
    var mapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        setOnClickListener()

        mCurrentLat = Prefrences.getPreferences(this, Constants.LATEST_LAT)!!.toDouble()
        mCurrentLon = Prefrences.getPreferences(this, Constants.LATEST_LON)!!.toDouble()

        binding.edtAddress.setText(Utils.getAddress(mCurrentLat!!,mCurrentLon!!, this).toString())
    }

    private fun setOnClickListener() {
        binding.relBackButton.setOnClickListener(this)
        binding.ivConfirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.relBackButton -> finish()
            R.id.ivConfirm -> goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        i.putExtra(Constants.OTHER_ADDRESS_LAT_LON, "")
        startActivity(i)
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(mCurrentLat!!, mCurrentLon!!)
        mMap.addMarker(MarkerOptions().position(sydney).title("Your Current Location"))
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14F))

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}