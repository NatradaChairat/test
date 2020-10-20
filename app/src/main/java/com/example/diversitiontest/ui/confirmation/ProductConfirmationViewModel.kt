package com.example.diversitiontest.ui.confirmation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.diversitiontest.data.repository.Repository
import com.example.diversitiontest.model.common.Status
import com.example.diversitiontest.util.combineWithNotNull
import java.text.DecimalFormat

class ProductConfirmationViewModel (private val repository: Repository) : ViewModel() {

    val productId = MutableLiveData<String>()
    val quantity = MutableLiveData<Int>()
    val address = MutableLiveData<String>()

    val displayTotal = MutableLiveData<String>()

    val product = Transformations.switchMap(productId){
        Transformations.map(repository.getProduct(it)) { response ->
            if (response.status == Status.SUCCESS) {
                val total = response.data?.price?.times(quantity.value ?: 0)
                val dec = DecimalFormat("#,###.00")
                displayTotal.value = dec.format(total)
                return@map  response.data
            } else {
                null
            }
        }

    }

    fun purchase() = Transformations.switchMap(productId.combineWithNotNull(quantity){ productId, quantity ->
        Pair(productId, quantity)
    }){ (productId, quantity) ->
        repository.purchase(productId, quantity)
    }

}