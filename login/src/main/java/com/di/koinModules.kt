package com.di

import com.login.LoginJourneySharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginJourneySharedViewModel() }
}