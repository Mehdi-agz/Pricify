package com.example.pricify.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.pricify.R
import com.example.pricify.data.DataSource
import com.example.pricify.model.Wishlist


class ItemAdapter(

    private val context: Context?,
    public val name: String,
    private val index: Int,
    val wishlists: List<Wishlist> = DataSource.wishlists


    ): RecyclerView.Adapter<ItemAdapter.WishItemViewHolder>() {


    class WishItemViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val item_image: ImageView = view!!.findViewById(R.id.item_image)
        val item_name: TextView = view!!.findViewById(R.id.item_name)
        val item_price: TextView = view!!.findViewById(R.id.item_price)
        val item_price_drop: TextView = view!!.findViewById(R.id.item_price_drop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishItemViewHolder {
        return WishItemViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.wishitem, parent, false))
    }

    override fun onBindViewHolder(holder: WishItemViewHolder, position: Int) {
        holder.item_name.text = wishlists[index].items[position].name
        holder.item_image.setImageResource(wishlists[index].items[position].imageResourceId)
        holder.item_price.text = wishlists[index].items[position].price.toString()
        holder.item_price_drop.text = wishlists[index].items[position].priceDrop.toString()
    }

    override fun getItemCount(): Int {
        return wishlists[index].items.size
    }
}