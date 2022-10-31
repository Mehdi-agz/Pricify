package com.example.pricify

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.databinding.ActivityRegisterBinding
import com.example.pricify.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mAuth:FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.banner.setOnClickListener {launchMain()}
        binding.register.setOnClickListener {registeruser()}

    }

    private fun registeruser(){
        var email:String = binding.email.text.toString().trim()
        var password:String = binding.password.text.toString().trim()
        var fullName:String = binding.fullName.text.toString().trim()

        if(fullName.isEmpty()){
            binding.fullName.error = "Full name is required!";
            binding.fullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            binding.email.error = "Email is required!";
            binding.email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.error = "Please provide valid email!";
            binding.email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            binding.password.error = "Password is required!";
            binding.password.requestFocus();
            return;
        }

        if(password.length < 6){
            binding.password.error = "Password must be at least 6 characters!";
            binding.password.requestFocus();
            return;
        }

        binding.progressBar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
            if (task.isSuccessful) {
                var user = User(fullName, email)
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(user).addOnCompleteListener{task->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "User has been registered successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                        launchLogin()
                    } else {
                        Toast.makeText(
                            this,
                            task.exception?.message ?: "Exception without message",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE

                    }
                }
            } else {

                Toast.makeText(this, task.exception?.message ?: "Exception without message", Toast.LENGTH_LONG).show()

                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun launchMain() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun launchLogin() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}