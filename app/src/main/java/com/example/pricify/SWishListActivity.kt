package com.example.pricify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.pricify.databinding.ActivityItemsBinding
import com.example.pricify.adaptor.ItemAdapter
import com.example.pricify.databinding.WishitemListBinding

class SWishListActivity : AppCompatActivity() {
    private lateinit var binding: WishitemListBinding
    private lateinit var listIntent: Intent

    private lateinit var name : String
    private var index : Int = 0
    private lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // processes artist concert data transferred from the adapter
        name = intent.getStringExtra("wishlistName").toString()
        index = intent.getIntExtra("index", 1)


        binding = WishitemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // passes concert data to new recycler view
        binding.wishRecyclerView.adapter = ItemAdapter(
            applicationContext, name, index
        )

        // Enable up button for backward navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

}
