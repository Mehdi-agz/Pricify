package com.example.pricify

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity: AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = Firebase.auth

        binding.banner.setOnClickListener {launchMain()}
        binding.register.setOnClickListener {launchRegister()}
        binding.login.setOnClickListener{login()}

        binding.email.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }

        binding.password.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }

    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
    }

    private fun login(){
        var email:String = binding.email.text.toString().trim()
        var password:String = binding.password.text.toString().trim()



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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task->
            if(task.isSuccessful){
                var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser;
                //if(user!!.isEmailVerified){
                    launchHome()
//                }else{
//                    user.sendEmailVerification().addOnCompleteListener{ task2->
//                        if(task2.isSuccessful){
//                            Toast.makeText(this, "Check your email to verify your account!", Toast.LENGTH_LONG).show()
//                        }else{
//                            Toast.makeText(this, "Error sending verification email! " + task2.exception!!.message.toString(), Toast.LENGTH_LONG).show()
//                        }
//                        binding.progressBar.visibility = View.GONE
//                    };

                //}
            }else{
                Toast.makeText(
                    this,
                    "Failed to login! Please check your credentials.",
                    Toast.LENGTH_LONG
                ).show()            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun launchRegister() {
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun launchMain() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun launchHome(){
        var intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}