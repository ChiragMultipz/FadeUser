package com.eclipsa.fade.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.eclipsa.fade.MainActivity
import com.eclipsa.fade.R
import com.eclipsa.fade.ui.get_started.GetStartedActivity
import com.eclipsa.fade.ui.get_started_new.GetStartedNewActivity
import com.eclipsa.fade.utils.Constants
import com.eclipsa.fade.utils.ManagePermissions
import com.eclipsa.fade.utils.Prefrences
import com.eclipsa.fade.utils.Utils.toast

class SplashActivity : AppCompatActivity() {

    private val TAG = "Permission"
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    var userId: String? = null
    val list = listOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        managePermissions = ManagePermissions(this, list, PermissionsRequestCode)
        setUpNavigation()
    }

    private fun setUpNavigation() {

        userId = Prefrences.getPreferences(application, Constants.USER_ID)

        if (userId!!.isNotEmpty()) {
            // Log.e(TAG,it.uid.toString() +".."+ it.name)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (managePermissions.checkPermissions()) {
                    Handler().postDelayed({
                        val intent = Intent(this, com.eclipsa.fade.MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 2000)
                }
        } else {
            if (managePermissions.checkPermissions()) {
                Handler().postDelayed({
                    val intent = Intent(this, GetStartedNewActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionsRequestCode -> {
                val isPermissionsGranted = managePermissions
                    .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    setUpNavigation()
                } else {
                    this.toast("Permissions denied.")

                    if (userId!!.isNotEmpty()) {
                        Handler().postDelayed({
                            val intent = Intent(this, com.eclipsa.fade.MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 2000)
                    } else {

                        Handler().postDelayed({
                            val intent = Intent(this, GetStartedNewActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 2000)

                    }
                }
                return
            }
        }
    }

}