package com.example.pricify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.pricify.databinding.ActivityItemsBinding
import com.example.pricify.adaptor.ItemAdapter
import com.example.pricify.adaptor.WishlistsAdaptor
import com.example.pricify.data.DataSource
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SWishListActivity : AppCompatActivity(), RefreshDataInterface {
    private lateinit var binding: ActivityItemsBinding
    private lateinit var listIntent: Intent

    private lateinit var name : String
    private var index : Int = 0
    private lateinit var image : ImageView

    private var adaptor: ItemAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // processes artist concert data transferred from the adapter
        name = intent.getStringExtra("wishlistName").toString()
        index = intent.getIntExtra("index", 1)


        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // passes concert data to new recycler view
        binding.itemsVerticalRecyclerView.adapter = ItemAdapter(
            applicationContext, name, index, this
        )

        binding.addItem.setOnClickListener { launchAddItem() }

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
                adaptor = ItemAdapter(
                    applicationContext, name, index, getDataInterface
                )
                binding.itemsVerticalRecyclerView.adapter = adaptor
                adaptor!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun launchAddItem() {
        listIntent = Intent(this, AddItemActivity::class.java)
        listIntent.putExtra("WishlistName", name)
        startActivity(listIntent)
    }

}
