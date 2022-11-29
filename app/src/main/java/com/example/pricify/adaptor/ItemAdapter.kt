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
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.ResourceManagerInternal.get
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewTreeLifecycleOwner.get
import androidx.recyclerview.widget.RecyclerView
import com.example.pricify.R
import com.example.pricify.RefreshDataInterface
import com.example.pricify.SWishListActivity
import com.example.pricify.data.DataSource
import com.example.pricify.data.DataSource.wishlists
import com.example.pricify.databinding.ActivityItemsBinding
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.lang.reflect.Array.get


class ItemAdapter(

    val context: Context?,
    val name: String,
    private val index: Int,
    val dataInterface: RefreshDataInterface,
    var wishlists: MutableList<Wishlist> = DataSource.wishlists


): RecyclerView.Adapter<ItemAdapter.WishItemViewHolder>() {


    inner class WishItemViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val item_image: ImageButton = view!!.findViewById(R.id.item_image)
        val item_name: TextView = view!!.findViewById(R.id.item_name)
        val item_price: TextView = view!!.findViewById(R.id.item_price)
        val item_price_drop: TextView = view!!.findViewById(R.id.item_price_drop)
        val removeButton: AppCompatImageButton = view!!.findViewById(R.id.delete_image)
        val wishListName : String = wishlists[index].name
        init {
            removeButton.setOnClickListener{
                removeItem(view, dataInterface,wishListName,item_name.text.toString())
            }
        }
        private fun removeItem(view: View?, dataInterface: RefreshDataInterface, wishlistName:String, itemName:String) {
            FirebaseDatabase.getInstance().getReference("Wishlists").child(
                FirebaseAuth.getInstance().currentUser!!.uid
            ).child(wishlistName).child(itemName).ref.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        view!!.context,
                        "Wishlist item removed!",
                        Toast.LENGTH_LONG
                    ).show()
                    dataInterface.refreshData()
                } else {
                    Toast.makeText(
                        view!!.context,
                        task.exception?.message ?: "Exception without message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


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
            if(isValid(item.url)) {
                val myUrl = Intent(Intent.ACTION_VIEW)
                myUrl.data = Uri.parse(item.url)
                holder.itemView.context.startActivity(myUrl)
            }
        }
    }

    private fun isValid(url : String) : Boolean {
        val isURL = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)".toRegex()
        return isURL.containsMatchIn(url)
    }



    override fun getItemCount(): Int {
        return if(wishlists.size==0) 0 else wishlists[index].items.size
    }

}
