package com.example.diversitiontest.di

import com.example.diversitiontest.ui.confirmation.ProductConfirmationViewModel
import com.example.diversitiontest.ui.home.HomeViewModel
import com.example.diversitiontest.ui.product.ProductViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ProductViewModel(get()) }
    viewModel { ProductConfirmationViewModel(get()) }
}

val modules = listOf(
    mainModule,
    serviceModule,
    networkModule,
    repositoryModule
)