package com.database.di

import com.database.Database
import com.database.FirestoreRepository
import org.koin.dsl.module

val firestoreModule = module {
    single<FirestoreRepository> { Database() }
}