package com.example.pricify.adaptor

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.appcompat.widget.ResourceManagerInternal.get
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewTreeLifecycleOwner.get
import androidx.recyclerview.widget.RecyclerView
import com.example.pricify.R
import com.example.pricify.SWishListActivity
import com.example.pricify.data.DataSource
import com.example.pricify.data.DataSource.wishlists
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist
import com.squareup.picasso.Picasso
import java.lang.reflect.Array.get


class ItemAdapter(

    val context: Context?,
    val name: String,
    private val index: Int,
    var wishlists: MutableList<Wishlist> = DataSource.wishlists,


): RecyclerView.Adapter<ItemAdapter.WishItemViewHolder>() {


    class WishItemViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val item_image: ImageButton = view!!.findViewById(R.id.item_image)
        val item_name: TextView = view!!.findViewById(R.id.item_name)
        val item_price: TextView = view!!.findViewById(R.id.item_price)
        val item_price_drop: TextView = view!!.findViewById(R.id.item_price_drop)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishItemViewHolder {

        return WishItemViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.wishitem, parent, false))
    }

    override fun onBindViewHolder(holder: WishItemViewHolder, position: Int) {
        val item = wishlists[index].items[position]
        val resources = context?.resources
        holder.item_name.text = wishlists[index].items[position].name
//        holder.item_image.setImageResource(wishlists[index].items[position].imageResourceId)
//        holder.item_url.text = wishlists[index].items[position].url
        holder.item_price.text = resources?.getString(R.string.item_price, item.price)
        holder.item_price_drop.text = resources?.getString(R.string.price_drop, item.priceDrop)

        var url = wishlists[index].items[position].imageUrl
        if (url.isEmpty()) {
            url = resources!!.getString(R.string.empty_img_url)
        }
        Picasso.with(holder.itemView.context).load(url).into(holder.item_image)

        holder.item_image.setOnClickListener {
            val myUrl = Intent(Intent.ACTION_VIEW)
            myUrl.data = Uri.parse(item.url)
            holder.itemView.context.startActivity(myUrl)
        }
    }



    override fun getItemCount(): Int {
        return if(wishlists.size==0) 0 else wishlists[index].items.size
    }

}
