package com.example.pricify

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import com.example.pricify.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener { launchLogin() }
        binding.signupBtn.setOnClickListener { launchRegister() }

        if (FirebaseAuth.getInstance().currentUser != null) {
            launchHome();
            return;
        }

    }

    private fun launchHome() {
        var intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
    private fun launchLogin() {
        scaler(binding.loginBtn)
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun launchRegister() {
        scaler(binding.signupBtn)
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun scaler(btn: Button) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,  0.75f, 1f, 1f)
        val scaleY =  PropertyValuesHolder.ofFloat(View.SCALE_Y,  1.25f, 1f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(btn, scaleX, scaleY)
        animator.start()

    }
}

