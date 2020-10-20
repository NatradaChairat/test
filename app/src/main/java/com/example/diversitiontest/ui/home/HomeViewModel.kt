package com.example.diversitiontest.ui.home

import androidx.lifecycle.ViewModel
import com.example.diversitiontest.data.repository.Repository

class HomeViewModel (private val repository: Repository) : ViewModel() {

    val products = repository.getProducts()


}