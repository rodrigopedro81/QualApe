package com.thedev.app

import android.app.Application
import com.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QualApeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QualApeApplication)
            modules(loginModule)
        }
    }
}
