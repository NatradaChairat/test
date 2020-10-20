package com.example.diversitiontest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.annotation.MainThread
import com.example.diversitiontest.data.api.ApiEmptyResponse
import com.example.diversitiontest.data.api.ApiErrorResponse
import com.example.diversitiontest.data.api.ApiResponse
import com.example.diversitiontest.data.api.ApiSuccessResponse
import com.example.diversitiontest.model.common.Resource

abstract class NetworkResource<RequestType>
@MainThread constructor() {

//    private val result = MutableLiveData<Resource<RequestType>>()
    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.value = Resource.loading(null)
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.value = when (response) {
                is ApiSuccessResponse -> {
                    Resource.success(response.body)
                }
                is ApiEmptyResponse -> {
                    Resource.success(null)
                }
                is ApiErrorResponse -> {
                    Resource.error(response.errorMessage, null, response.statusCode)
                }
            }
        }

    }

    fun asLiveData() = result as LiveData<Resource<RequestType>>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}