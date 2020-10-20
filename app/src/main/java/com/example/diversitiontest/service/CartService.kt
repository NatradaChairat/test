package com.example.diversitiontest.service

import androidx.lifecycle.LiveData
import com.example.diversitiontest.data.api.ApiResponse
import retrofit2.http.POST

interface CartService {

    @POST("purchase")
    fun purchase() : LiveData<ApiResponse<String>>

}