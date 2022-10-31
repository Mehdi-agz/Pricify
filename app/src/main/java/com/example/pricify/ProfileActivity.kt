package com.example.pricify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.pricify.databinding.ActivityHomeBinding
import com.example.pricify.databinding.ActivityProfileBinding
import com.example.pricify.model.User
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var user:FirebaseUser
    private lateinit var reference:DatabaseReference
    private lateinit var userId:String

    private lateinit var greeting:TextView
    private lateinit var name:TextView
    private lateinit var email:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        greeting = findViewById(R.id.greeting)
        name = findViewById(R.id.name)
        email = findViewById(R.id.emailAddress)
        user = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user!!.uid;
        reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var userProfile:User = snapshot.getValue(User::class.java) as User
                if(userProfile!=null){
                    val nameStr:String = userProfile.fullName;
                    val emailStr:String = userProfile.email;

                    greeting.text = "Welcome, " + nameStr + "!";
                    name.text = nameStr;
                    email.text = emailStr;
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Something wrong happened! " + error!!.message, Toast.LENGTH_LONG).show()
            }

        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}