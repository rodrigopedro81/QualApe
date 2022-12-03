package com.thedev.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@ChallengeApplication)
//            modules(viewModelsModule)
//            modules(interactorsModule)
//            modules(repositoriesModule)
        }
    }

//    private val viewModelsModule = module {
//        viewModel { RandomActivityViewModel(get()) }
//        viewModel { HomeViewModel(get()) }
//        viewModel { MyActivitiesViewModel(get()) }
//    }
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
