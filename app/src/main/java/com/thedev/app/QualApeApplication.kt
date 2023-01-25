package com.thedev.app

import android.app.Application
import com.authentication.authenticationModule
import com.database.di.firestoreModule
import com.di.loginModule
import com.google.firebase.FirebaseApp
import com.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class QualApeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@QualApeApplication)
            modules(
                modules = listOf(
                    loginModule,
                    homeModule,
                    firestoreModule,
                    authenticationModule
                )
            )
        }
    }
}
