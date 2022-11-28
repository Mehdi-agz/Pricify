package com.example.pricify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.pricify.SWishListActivity
import com.example.pricify.adaptor.WishlistsAdaptor
import com.example.pricify.data.DataSource
import com.example.pricify.databinding.ActivityWishlistsBinding
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

interface RefreshDataInterface{
    fun refreshData();
}

class WishListsActivity : AppCompatActivity(), RefreshDataInterface {

    private lateinit var binding: ActivityWishlistsBinding
    private lateinit var listIntent: Intent
    private var adaptor: WishlistsAdaptor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("Oncreate!")


//        binding.single_wishlist_btn.setOnClickListener { launchWishlist() }
        binding.addWishlist.setOnClickListener { launchAddWishlist() }

    }
    private fun launchAddWishlist() {
        listIntent = Intent(this, AddWishListActivity::class.java)
        startActivity(listIntent)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }


    override fun refreshData(){
        val dbref = FirebaseDatabase.getInstance().getReference("Wishlists").child(FirebaseAuth.getInstance().currentUser!!.uid).ref
        val getDataInterface = this
        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                DataSource.wishlists.clear()
                if(snapshot.exists()){
                    for(wishlistSnapshot in snapshot.children){
                        var itemList:MutableList<Item> = mutableListOf()
                        var name:String = ""
                        for(itemSnapshot in wishlistSnapshot.children){
                            if(itemSnapshot.key=="name"){
                                name = itemSnapshot.value.toString()
                                continue
                            }
                            val item = itemSnapshot.getValue(Item::class.java)
                            itemList.add(item!!)
                        }
                        if(name.isEmpty()){
                            throw Error("NAME NOT FOUND IN DATABASE!")
                        }
                        DataSource.wishlists.add(Wishlist(name, itemList))
                    }
                }
                adaptor = WishlistsAdaptor(
                    applicationContext, getDataInterface
                )
                binding.wishlistsVerticalRecyclerView.adapter = adaptor

                adaptor!!.updateItems()
                adaptor!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}

