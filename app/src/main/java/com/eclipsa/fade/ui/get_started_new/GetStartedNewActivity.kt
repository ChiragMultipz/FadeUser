package com.eclipsa.fade.ui.get_started_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.eclipsa.fade.R
import com.eclipsa.fade.databinding.ActivityGetStartedNewBinding
import com.eclipsa.fade.databinding.ActivityProfileBinding
import com.eclipsa.fade.ui.get_started.GetStartedActivity

class GetStartedNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedNewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGetStartedNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.relGetStarted.setOnClickListener {
            startActivity(Intent(this, GetStartedActivity::class.java))
        }
    }
}