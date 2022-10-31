package com.example.pricify.data

import com.example.pricify.R
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist

object DataSource {
    val wishlists: List<Wishlist> = listOf(
        Wishlist(
            "Shoes",
            items = listOf(
                Item(R.drawable.airmax0,"Nike Air Max", 125, 30),
                Item(R.drawable.airmax1,"Nike Colorful Jordan's", 111, 42),
                Item(R.drawable.airmax2,"Nike Skater Schoe", 99, 10),
            )
        ),
        Wishlist(
            "Tshirts",
            items = listOf(
                Item(R.drawable.nikeshirt0,"Mens Nike Shirt", 20, 10),
                Item(R.drawable.adidasshit0,"MEN'S ADIDAS ORIGINALS ITASCA", 50, 5),
                Item(R.drawable.adidasshirt1,"MEN'S ADIDAS GERMANY JERSEY", 65, 0),
                Item(R.drawable.adidasshirt2,"MEN'S NIKE SMILING SWOOSH T-SHIRT", 20, 10),
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