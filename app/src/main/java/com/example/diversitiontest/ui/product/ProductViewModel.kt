package com.example.diversitiontest.ui.product

import androidx.lifecycle.*
import com.example.diversitiontest.data.repository.Repository
import com.example.diversitiontest.model.common.Status

class ProductViewModel (private val repository: Repository) : ViewModel() {

    val productId = MutableLiveData<String>()

    val quantity = MutableLiveData<Int>()

    val product = Transformations.switchMap(productId){
        Transformations.map(repository.getProduct(it)) { response ->
            if (response.status == Status.SUCCESS) {
                response.data
            } else {
                null
            }
        }

    }





}