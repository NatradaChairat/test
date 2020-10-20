package com.example.diversitiontest.service

import androidx.lifecycle.LiveData
import com.example.diversitiontest.data.api.ApiResponse
import com.example.diversitiontest.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    fun getAllProduct() : LiveData<ApiResponse<List<Product>>>

    @GET("product/detail?")
    fun getProduct(@Query("product_id") productId: String) : LiveData<ApiResponse<Product>>
}
