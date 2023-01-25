package com.di

import com.viewModel.LoginViewModule
import com.viewModel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { LoginViewModule(get(), get()) }
}