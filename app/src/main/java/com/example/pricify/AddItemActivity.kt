package com.example.pricify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pricify.databinding.ActivityAddItemBinding
import android.widget.CheckBox


class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private lateinit var listIntent: Intent


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

        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
