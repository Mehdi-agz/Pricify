package com.example.pricify

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.pricify.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var listIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.profileBtn.setOnClickListener {launchProfile()}
        binding.ordersBtn.setOnClickListener {launchOrders()}
        binding.paymentBtn.setOnClickListener {launchPayments()}
        binding.accPrivacyBtn.setOnClickListener {launchAccount()}

        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    private fun launchAccount() {
        scaler(binding.accPrivacyBtn)
    }

    private fun launchPayments() {
        scaler(binding.paymentBtn)
    }

    private fun launchOrders() {
        scaler(binding.ordersBtn)
    }

    private fun launchProfile() {
        scaler(binding.profileBtn)
        var intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun scaler(btn: Button) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,  0.75f, 1f, 1f)
        val scaleY =  PropertyValuesHolder.ofFloat(View.SCALE_Y,  1f, 1f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(btn, scaleX, scaleY)
        animator.start()

    }
}

