package com.example.pricify

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.pricify.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedbackBinding
    private lateinit var listIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.profileBtn.setOnClickListener {launchProfile()}
        binding.paymentBtn.setOnClickListener {launchPayments()}
        binding.accPrivacyBtn.setOnClickListener {launchAccount()}
    }

    private fun launchAccount() {
        scaler(binding.accPrivacyBtn)
    }

    private fun launchPayments() {
        scaler(binding.paymentBtn)
    }

    private fun launchProfile() {
        scaler(binding.profileBtn)
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun scaler(btn: Button) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,  0.95f, 1f, 1f)
        val scaleY =  PropertyValuesHolder.ofFloat(View.SCALE_Y,  1f, 1f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(btn, scaleX, scaleY)
        animator.start()

    }
}

