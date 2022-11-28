package com.example.pricify

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.data.DataSource
import com.example.pricify.databinding.ActivityHomeBinding
import com.example.pricify.model.Item
import com.example.pricify.model.User
import com.example.pricify.model.Wishlist
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
        binding.profileBtn.setOnClickListener { lunchProfile() }
        binding.feedbackBtn.setOnClickListener { lunchFeedback() }
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
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }



    private fun launchMain() {
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
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
        scaler(binding.logoutBtn)
        logoutConfirmationAlert()!!.show()
    }


    private fun lunchFeedback() {
        scaler(binding.profileBtn)
        val url = "https://docs.google.com/forms/d/e/1FAIpQLSd3DCw8xqWSzQDY25Blc--_eWKInZg_k9c7Y76f-ShgU4Y0CQ/viewform"
        val myUrl = Intent(Intent.ACTION_VIEW)
        myUrl.data = Uri.parse(url)
        startActivity(myUrl)
    }

    private fun lunchProfile() {
        scaler(binding.profileBtn)
        var intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun launchWishLists() {
        scaler(binding.wishlistsBtn)
        var intent = Intent(this, WishListsActivity::class.java)
        startActivity(intent)
    }

    private fun scaler(btn: Button) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,  0.95f, 1f, 1f)
        val scaleY =  PropertyValuesHolder.ofFloat(View.SCALE_Y,  1f, 1f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(btn, scaleX, scaleY)
        animator.start()

    }
}