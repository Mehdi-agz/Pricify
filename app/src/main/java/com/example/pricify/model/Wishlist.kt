package com.example.pricify.model

data class Wishlist (
    val name: String,
    val items: MutableList<Item>,
    val item_count: Int = items.size,
    val total_price: Double = items.sumOf { it.price },
)