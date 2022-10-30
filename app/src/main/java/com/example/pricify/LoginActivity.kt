package com.example.pricify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.databinding.ActivityLoginBinding


class LoginActivity: AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.register.setOnClickListener {launchRegister()}
    }

    private fun launchRegister() {
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}