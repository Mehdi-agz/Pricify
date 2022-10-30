package com.example.pricify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding
    private lateinit var listIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wishlistsBtn.setOnClickListener { lunchWishLists() }

//        binding.settingsBtn.setOnClickListener { lunchSettings() }

//        binding.logoutBtn.setOnClickListener { lunchLogOut() }

        // Enable up button for backward navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

//    private fun lunchLogOut() {
//        TODO("Not yet implemented")
//    }

//    private fun lunchSettings() {
//        listIntent = Intent(this, SettingsActivity::class.java)
//        startActivity(listIntent)
//    }

    private fun lunchWishLists() {
        listIntent = Intent(this, WishListsActivity::class.java)
        startActivity(listIntent)
    }
}