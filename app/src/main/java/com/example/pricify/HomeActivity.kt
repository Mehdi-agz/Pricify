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

        binding.wishlistsBtn.setOnClickListener { launchWishLists() }

        binding.logoutBtn.setOnClickListener { logOut() }
        binding.settingsBtn.setOnClickListener { lunchSettings() }

//        binding.logoutBtn.setOnClickListener { lunchLogOut() }

        // Enable up button for backward navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

//    private fun logoutConfirmationAlert(): AlertDialog? {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Logout Confirmation")
//        builder.setMessage("Are you sure you want to logout?")
////        builder.setPositiveButton("Yes", DialogInterface.OnClickListener()
////
////        builder.setPositiveButton("Yes") { dialog, which ->
////            Toast.makeText(applicationContext,
////                android.R.string.ok, Toast.LENGTH_SHORT).show()
////        }
////
////        builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
////            Toast.makeText(applicationContext,
////                android.R.string.cancel, Toast.LENGTH_SHORT).show()
////        }
////
////        return builder.create()
//    }

    private fun logOut(){

    }

    private fun logOutConfirmed() {
        FirebaseAuth.getInstance().signOut();
        launchMain();
    }

        private fun launchMain(){
        listIntent = Intent(this, MainActivity::class.java)
        startActivity(listIntent)
    }
    private fun lunchSettings() {
        listIntent = Intent(this, SettingsActivity::class.java)
        startActivity(listIntent)
    }

    private fun launchWishLists() {
        listIntent = Intent(this, WishListsActivity::class.java)
        startActivity(listIntent)
    }
}