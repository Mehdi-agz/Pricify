package com.example.pricify.model

import androidx.annotation.DrawableRes

data class Item (
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val price: Int,
    val priceDrop: Int,
)