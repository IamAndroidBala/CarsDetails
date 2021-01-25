package com.sevenpeakssoftware.carsdetails

import android.app.Application
import com.sevenpeakssoftware.carsdetails.di.startCarsKoin

class CarsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startCarsKoin(this)


    }

}