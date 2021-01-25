package com.sevenpeakssoftware.carsdetails.di

import android.content.ComponentCallbacks
import android.content.Context
import com.sevenpeakssoftware.carsdetails.BuildConfig
import com.sevenpeakssoftware.carsdetails.data.database.CarsDatabase
import com.sevenpeakssoftware.carsdetails.data.database.DatabaseFactory
import com.sevenpeakssoftware.carsdetails.data.network.api.RetrofitFactory
import com.sevenpeakssoftware.carsdetails.data.network.api.api.cars.CarsApi
import com.sevenpeakssoftware.carsdetails.data.repository.CarsRepository
import com.sevenpeakssoftware.carsdetails.domain.cars.CarsUseCase
import com.sevenpeakssoftware.carsdetails.ui.cars.CarsViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit

fun ComponentCallbacks.startCarsKoin(androidContext: Context) {
    startKoin(
        androidContext, listOf(
            carsDatabaseModule,
            carsWebserviceModule,
            carsRepositoriesModule,
            carsUseCasesModule,
            viewModelModule
        )
    )
}

private val carsWebserviceModule = module {
    single("carsWebserviceModuleRetrofit") {
        RetrofitFactory().create(
            BuildConfig.CARS_WEBSERVICE_BASE_URL,
            BuildConfig.DEBUG
        )
    }
    single { get<Retrofit>("carsWebserviceModuleRetrofit").create(CarsApi::class.java) }
}

private val carsDatabaseModule = module {
    single { DatabaseFactory().create(get()) }
    single { get<CarsDatabase>().carsDao() }
}

private val carsRepositoriesModule = module {
    single {
        CarsRepository(
            get(),
            get()
        )
    }
}

private val carsUseCasesModule = module {
    factory { CarsUseCase(get()) }
}

private val viewModelModule = module {
    viewModel { CarsViewModel(get(), get()) }
}