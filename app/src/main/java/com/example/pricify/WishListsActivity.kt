package com.example.pricify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pricify.SWishListActivity
import com.example.pricify.adaptor.WishlistsAdaptor
import com.example.pricify.databinding.ActivityWishlistsBinding

class WishListsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWishlistsBinding
    private lateinit var listIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWishlistsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wishlistsVerticalRecyclerView.adapter = WishlistsAdaptor(
            applicationContext
        )

//        binding.single_wishlist_btn.setOnClickListener { launchWishlist() }
        binding.addWishlist.setOnClickListener { launchAddWishlist() }
        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    private fun launchAddWishlist() {
        listIntent = Intent(this, AddWishListActivity::class.java)
        startActivity(listIntent)
    }

    private fun launchWishlist() {
        listIntent = Intent(this, SWishListActivity::class.java)
        startActivity(listIntent)
    }
}

