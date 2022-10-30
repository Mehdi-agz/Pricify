package com.example.pricify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pricify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener { launchLogin() }
        binding.signupBtn.setOnClickListener {launchRegister()}
        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    private fun launchLogin() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun launchRegister() {
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}

