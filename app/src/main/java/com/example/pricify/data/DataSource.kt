package com.example.pricify.data

import com.example.pricify.R
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist

object DataSource {
    val wishlists: List<Wishlist> = listOf(
        Wishlist(
            "Shoes",
            items = listOf(
                Item(R.drawable.airmax0,"Nike Air Max", 125, 25),
                Item(R.drawable.airmax1,"Nike Air Max", 125, 25),
                Item(R.drawable.airmax2,"Nike Air Max", 125, 25),
            )
        ),
        Wishlist(
            "Tshirts",
            items = listOf(
                Item(R.drawable.nikeshirt0,"Nike Shirt", 125, 25)
//                item(R.drawable.Nikeshirt1,"Nike shirt", 125, 25),
//                item(R.drawable.Nikeshirt2,"Nike shirt", 125, 25),
            )
        ),
//        Wishlist(
//            "Sweaters",
//            items = listOf(
//                item(R.drawable.NikeSweater,"Nike Sweater", 125, 25),
//                item(R.drawable.NikeSweater,"Nike Sweater", 125, 25),
//                item(R.drawable.NikeSweater,"Nike Sweater", 125, 25),
//            )
//        )
    )
}