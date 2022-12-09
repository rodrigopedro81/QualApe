package com.di

import com.login.LoginJourneyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginJourneyViewModel() }
}