package com.example.diversitiontest.model

import java.text.DecimalFormat

data class Product(
    val createdAt: String? = null,
    val imageUrl: String? = null,
    val price: Double? = null,
    val productId: String? = null,
    val productName: String? = null,
    val stock: Int? = null,
    val updatedAt: String? = null,
    val coverImageUrl: String? = null
) {
    fun displayName(): String = "Name: $productName"

    fun displayPrice(): String {
        val dec = DecimalFormat("#,###.00")
        return "Price: ${dec.format(price)}  ฿"
    }

    fun displayPriceWithBath(): String {
        val dec = DecimalFormat("#,###.00")
        return "${dec.format(price)}  ฿"
    }

    fun displayStock(): String = "Stock: $stock"
}