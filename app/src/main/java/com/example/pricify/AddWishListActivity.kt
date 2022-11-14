package com.example.pricify

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.databinding.ActivityAddWishlistBinding
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AddWishListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWishlistBinding
    private lateinit var listIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveBtn.setOnClickListener { addWishlist() }

    }

    private fun addWishlist() {
        var name:String = binding.nameInput.text.toString().trim()

        if(name.isEmpty()){
            binding.nameInput.error = "Wishlist name is required!";
            binding.nameInput.requestFocus();
            return;
        }
        val userWishlistsRef = FirebaseDatabase.getInstance().getReference("Wishlists").child(FirebaseAuth.getInstance().currentUser!!.uid).ref
        val context = this
        userWishlistsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(name)) {
                    binding.nameInput.error = "Wishlist name already exists! Enter a unique identifier!";
                    binding.nameInput.requestFocus();
                    return;
                }else{
                    binding.progressBar.visibility = View.VISIBLE
                    var wishList = Wishlist(binding.nameInput.text.toString(),listOf<Item>())
                    val newWishlist = FirebaseDatabase.getInstance().getReference("Wishlists").child(FirebaseAuth.getInstance().currentUser!!.uid).child(wishList.name)
                    newWishlist.setValue(wishList).addOnCompleteListener{ task->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Wishlist successfully created!",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progressBar.visibility = View.GONE
                            launchWishlists()
                        } else {
                            Toast.makeText(
                                context,
                                task.exception?.message ?: "Exception without message",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })



    }

    private fun launchWishlists(){
        var intent = Intent(this, WishListsActivity::class.java)
        startActivity(intent)
    }
}
