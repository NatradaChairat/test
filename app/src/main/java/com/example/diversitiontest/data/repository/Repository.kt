package com.example.diversitiontest.data.repository

import androidx.lifecycle.LiveData
import com.example.diversitiontest.model.Product
import com.example.diversitiontest.model.common.Resource

interface Repository {
    fun getProducts(): LiveData<Resource<List<Product>>>
    fun getProduct(productId: String): LiveData<Resource<Product>>
    fun purchase(productId: String, quantity: Int): LiveData<Resource<String>>
}