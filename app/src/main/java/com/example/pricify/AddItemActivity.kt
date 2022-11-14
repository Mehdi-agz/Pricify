package com.example.pricify

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.databinding.ActivityAddItemBinding
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemPriceDrop.visibility = View.GONE

        binding.checkbox.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                binding.itemPriceDrop.visibility = View.VISIBLE
//                binding.priceDropText.visibility = View.VISIBLE
            } else {
                binding.itemPriceDrop.visibility = View.GONE
//                binding.priceDropText.visibility = View.GONE
            }
        }
        name = intent.getStringExtra("WishlistName").toString()
        Toast.makeText(this, name, Toast.LENGTH_LONG).show()

        binding.saveBtn.setOnClickListener { addItem() }

    }

    private fun addItem(){
        var name:String = binding.nameInput.text.toString().trim()
        var itemUrl:String = binding.itemUrlInput.text.toString().trim()
        var imageUrl:String = binding.itemImageUrlInput.text.toString().trim()
        var price:Double = binding.itemPriceInput.text.toString().trim().toDouble()
        var priceDrop:Double = if(binding.checkbox.isChecked) -1.0 else binding.priceDropInput.text.toString().trim().toDouble()

        if(name.isEmpty()){
            binding.nameInput.error = "Item name is required!";
            binding.nameInput.requestFocus();
            return;
        }
        if(itemUrl.isEmpty()){
            binding.nameInput.error = "Item url is required!";
            binding.nameInput.requestFocus();
            return;
        }
        if(imageUrl.isEmpty()){
            binding.nameInput.error = "Image url is required!";
            binding.nameInput.requestFocus();
            return;
        }
        if(price <=0.0){
            binding.nameInput.error = "Valid price is required!";
            binding.nameInput.requestFocus();
            return;
        }
        if(priceDrop==0.0){
            binding.nameInput.error = "Valid price drop is required!";
            binding.nameInput.requestFocus();
            return;
        }

        val userWishlistsRef = FirebaseDatabase.getInstance().getReference("Wishlists").child(
            FirebaseAuth.getInstance().currentUser!!.uid).child(name).ref
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
                    val newWishlist = FirebaseDatabase.getInstance().getReference("Wishlists").child(
                        FirebaseAuth.getInstance().currentUser!!.uid).child(wishList.name)
                    newWishlist.setValue(wishList).addOnCompleteListener{ task->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Wishlist successfully created!",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progressBar.visibility = View.GONE
                            //launchWishlists()
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

}
