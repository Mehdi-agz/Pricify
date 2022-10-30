package com.example.pricify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pricify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener { launchLogin() }

        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    private fun launchLogin() {
        listIntent = Intent(this, HomeActivity::class.java)
        startActivity(listIntent)
    }
}

