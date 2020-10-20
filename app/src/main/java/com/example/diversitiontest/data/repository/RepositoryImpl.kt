package com.example.diversitiontest.data.repository

import androidx.lifecycle.LiveData
import com.example.diversitiontest.data.api.ApiResponse
import com.example.diversitiontest.model.Product
import com.example.diversitiontest.model.common.Resource
import com.example.diversitiontest.service.CartService
import com.example.diversitiontest.service.ProductService

class RepositoryImpl(
    private val productService: ProductService,
    private val cartService: CartService
) : Repository {
    override fun getProducts(): LiveData<Resource<List<Product>>> = object :
        NetworkResource<List<Product>>() {
        override fun createCall(): LiveData<ApiResponse<List<Product>>> {
            return productService.getAllProduct()
        }
    }.asLiveData()

    override fun getProduct(productId: String): LiveData<Resource<Product>> = object :
        NetworkResource<Product>() {
        override fun createCall(): LiveData<ApiResponse<Product>> {
            return productService.getProduct(productId)
        }

    }.asLiveData()

    override fun purchase(productId: String, quantity: Int): LiveData<Resource<String>> = object :
        NetworkResource<String>() {
        override fun createCall(): LiveData<ApiResponse<String>> {
            return cartService.purchase()
        }

    }.asLiveData()
}