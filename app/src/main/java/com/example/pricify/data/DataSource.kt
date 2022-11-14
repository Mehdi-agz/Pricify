package com.example.pricify.data

import com.example.pricify.R
import com.example.pricify.model.Item
import com.example.pricify.model.Wishlist

object DataSource {
    val wishlists: List<Wishlist> = listOf(
        Wishlist(
            "Shoes",
            items = listOf(
                Item(R.drawable.airmax0,"Nike Air Max", "https://www.finishline.com/store/product/womens-nike-air-vapormax-plus-running-shoes/prod2772256?styleId=DZ5204&colorId=500", 210, 0),
                Item(R.drawable.airmax1,"Nike Colorful Jordan's", "https://www.finishline.com/store/product/womens-nike-air-vapormax-plus-running-shoes/prod2772256?styleId=DQ8588&colorId=800",210, 80),
                Item(R.drawable.airmax2,"NIKE AIR VAPORMAX PLUS", "https://www.finishline.com/store/product/womens-nike-air-vapormax-plus-running-shoes/prod2772256?styleId=DX2746&colorId=400",210, 0),
            )
        ),
//        Wishlist(
//            "Tshirts",
//            items = listOf(
//                Item(R.drawable.nikeshirt0,"Mens Nike Shirt", 20, 10),
//                Item(R.drawable.adidasshit0,"MEN'S ADIDAS ORIGINALS ITASCA", 50, 5),
//                Item(R.drawable.adidasshirt1,"MEN'S ADIDAS GERMANY JERSEY", 65, 0),
//                Item(R.drawable.adidasshirt2,"MEN'S NIKE SMILING SWOOSH T-SHIRT", 20, 10),
//            )
//        ),
    )
}