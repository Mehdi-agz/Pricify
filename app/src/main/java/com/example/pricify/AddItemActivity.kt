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
    private lateinit var wishlistName:String

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
        wishlistName = intent.getStringExtra("WishlistName").toString()

        binding.saveBtn.setOnClickListener { addItem() }

    }

    private fun addItem() {
        var name:String = binding.nameInput.text.toString().trim()
        var itemUrl:String = binding.itemUrlInput.text.toString().trim()
        var imageUrl:String = binding.itemImageUrlInput.text.toString().trim()
        val p = binding.itemPriceInput.text.toString().trim()
        var price:Double = if(p.isEmpty()) 0.0 else p.toDouble()
        var targetPriceDrop:Double = if(!binding.checkbox.isChecked) -1.0 else if (binding.priceDropInput.text.toString().isEmpty()) -1.0 else  binding.priceDropInput.text.toString().trim().toDouble()

        var exception = false
        if(name.isEmpty()){
            binding.nameInput.error = "Item name is required!";
            binding.nameInput.requestFocus();
            exception = true
        }
        if(itemUrl.isEmpty()){
            binding.itemUrlInput.error = "Item url is required!";
            binding.itemUrlInput.requestFocus()
            exception = true
        }
        if(price <= 0.0){
            binding.itemPriceInput.error = "Valid price is required!";
            binding.itemPriceInput.requestFocus()
            exception = true
        }
        if(targetPriceDrop==0.0){
            binding.itemPriceDrop.error = "Valid price drop is required!";
            binding.itemPriceDrop.requestFocus()
            exception = true
        }
        if (exception) {
            return
        }

        val userWishlistRef = FirebaseDatabase.getInstance().getReference("Wishlists").child(
            FirebaseAuth.getInstance().currentUser!!.uid).child(wishlistName).ref
        val context = this
        userWishlistRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(name)) {
                    binding.nameInput.error = "Item name already exists! Enter a unique identifier!";
                    binding.nameInput.requestFocus();
                    return;
                }else{
                    binding.progressBar.visibility = View.VISIBLE
                    var item = Item(0, name, itemUrl,price,0.0, targetPriceDrop, imageUrl)
                    val newItem = FirebaseDatabase.getInstance().getReference("Wishlists").child(
                        FirebaseAuth.getInstance().currentUser!!.uid).child(wishlistName).child(item.name)
                    newItem.setValue(item).addOnCompleteListener{ task->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Item added successfully!",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progressBar.visibility = View.GONE
                            finish()
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
