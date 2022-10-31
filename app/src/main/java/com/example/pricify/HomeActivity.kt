package com.example.pricify

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.pricify.databinding.ActivityHomeBinding
import com.example.pricify.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity(){
    private lateinit var user: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var userId:String
    private lateinit var binding: ActivityHomeBinding
    private lateinit var greeting: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wishlistsBtn.setOnClickListener { launchWishLists() }

        binding.logoutBtn.setOnClickListener { logOut() }
        binding.settingsBtn.setOnClickListener { lunchSettings() }

        greeting = findViewById(R.id.greetingMessage)
        user = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user!!.uid;
        reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var userProfile: User = snapshot.getValue(User::class.java) as User
                if(userProfile!=null){
                    val nameStr:String = userProfile.fullName;
                    greeting.text = "Welcome to Pricify " + nameStr.split(" ")[0] + "!"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Something wrong happened! " + error!!.message, Toast.LENGTH_LONG).show()
            }

        })
        // Enable up button for backward navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun launchMain() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun logOutConfirmed() {
        Toast.makeText(applicationContext,
            "Logging out!", Toast.LENGTH_SHORT).show()
        FirebaseAuth.getInstance().signOut();
        launchMain();
    }


    inner class ConfirmLogoutOnClickListener() : DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            logOutConfirmed()
        }


    }

    private fun logoutConfirmationAlert(): AlertDialog? {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout Confirmation")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Confirm", ConfirmLogoutOnClickListener())

        builder.setNegativeButton(android.R.string.cancel) { dialog, which ->

        }

        return builder.create()
    }

    private fun logOut(){
        logoutConfirmationAlert()!!.show()
    }


    private fun lunchSettings() {
        var intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun launchWishLists() {
        var intent = Intent(this, WishListsActivity::class.java)
        startActivity(intent)
    }
}