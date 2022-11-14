package com.example.pricify.model

import androidx.annotation.DrawableRes

data class Item (
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val url: String,
    val price: Double,
    val priceDrop: Double,
    val targetPriceDrop: Double,
    val imageUrl:String,

)