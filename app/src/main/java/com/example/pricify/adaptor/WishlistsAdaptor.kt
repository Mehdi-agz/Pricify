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

    private var dataset = mutableListOf<Wishlist>()



    // initialze view elements
    class WishlistsCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!){
        val button: Button = view!!.findViewById(R.id.single_wishlist_btn)
        val item_count: TextView = view!!.findViewById(R.id.item_count)
        val total_price: TextView = view!!.findViewById(R.id.total_price)
        init {
            println("WishlistCardViewHoler view holder!")
            button.setOnClickListener {
                val listIntent = Intent(view!!.context, SWishListActivity::class.java)
                var index = 0
                for (wishlist : Wishlist in DataSource.wishlists) {
                    if (wishlist.name == button.text) {
                        listIntent.putExtra("wishlistName", wishlist.name)
                        listIntent.putExtra("wishListCount", wishlist.item_count)
                        listIntent.putExtra("wishListPrice", wishlist.total_price)
                        listIntent.putExtra("index", index)
                        view.context.startActivity(listIntent)
                        break
                    }
                    index += 1
                }
            }
        }
    }

    fun updateItems(){
        println("Updating items holder!")
        dataset.clear()
        dataset=DataSource.wishlists
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistsCardViewHolder {
        println("Creating view holder!")
        return WishlistsCardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wishlists_vertical_list,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: WishlistsCardViewHolder, position: Int) {
        val wishlist = dataset[position]
        val resources = context?.resources

        holder.button.text = wishlist.name
        holder.item_count.text = resources?.getString(R.string.item_count, wishlist.item_count)
        holder.total_price.text = resources?.getString(R.string.total_price, wishlist.total_price)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}