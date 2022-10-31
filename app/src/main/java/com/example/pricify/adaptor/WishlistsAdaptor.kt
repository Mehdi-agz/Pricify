package com.example.pricify.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.pricify.SWishListActivity
import com.example.pricify.data.DataSource
import com.example.pricify.model.Wishlist
import com.example.pricify.R

class WishlistsAdaptor(

    private val context: Context?


): RecyclerView.Adapter<WishlistsAdaptor.WishlistsCardViewHolder>() {

    private val dataset = DataSource.wishlists



    // initialze view elements
    class WishlistsCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!){
        val button: Button = view!!.findViewById(R.id.single_wishlist_btn)

        init {
            button.setOnClickListener {
                val listIntent = Intent(view!!.context, SWishListActivity::class.java)
                var index = 0
                for (wishlist : Wishlist in DataSource.wishlists) {
                    if (wishlist.name == button.text) {
                        listIntent.putExtra("wishlistName", wishlist.name)
                        listIntent.putExtra("index", index)
                        view.context.startActivity(listIntent)
                        break
                    }
                    index += 1
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistsCardViewHolder {
        return WishlistsCardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wishlists_vertical_list,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: WishlistsCardViewHolder, position: Int) {
        val artist = dataset[position]
        holder.button.text = artist.name
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}