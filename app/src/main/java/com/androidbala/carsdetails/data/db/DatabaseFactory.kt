package com.androidbala.carsdetails.data.db

import android.content.Context
import androidx.room.Room

class DatabaseFactory {

    fun create(context: Context): CarsDatabase {
        return Room.databaseBuilder(context, CarsDatabase::class.java, "DB_CARS")
            .fallbackToDestructiveMigration()
            .build()
    }
}