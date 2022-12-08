package com.thedev.app

import android.app.Application
import com.login.LoginJourneyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class QualApeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QualApeApplication)
            modules(loginModule)
        }
    }

    private val loginModule = module {
        viewModel { LoginJourneyViewModel() }
    }
//
//    private val interactorsModule = module {
//        factory { RandomActivityInteractor(get()) }
//        factory { HomeInteractor(get()) }
//        factory { MyActivitiesInteractor() }
//    }
//
//    private val repositoriesModule = module {
//        factory<ActivityRepository> { ActivityRepositoryImpl(RetrofitInstance.api) }
//    }
}
