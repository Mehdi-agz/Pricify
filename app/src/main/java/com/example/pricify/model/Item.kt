package com.example.pricify.model

import androidx.annotation.DrawableRes

data class Item (
    @DrawableRes val imageResourceId: Int = 0,
    val name: String= "",
    val url: String= "",
    val price: Double= 0.0,
    val priceDrop: Double= 0.0,
    val targetPriceDrop: Double= 0.0,
    val imageUrl:String= "",

)