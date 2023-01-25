package com.firestore.di

import com.firestore.Database
import com.firestore.FirestoreRepository
import org.koin.dsl.module

val firestoreModule = module {
    single<FirestoreRepository> { Database() }
}