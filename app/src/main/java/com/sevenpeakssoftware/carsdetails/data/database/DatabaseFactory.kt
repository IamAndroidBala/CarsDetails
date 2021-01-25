package com.sevenpeakssoftware.carsdetails.data.database

import android.content.Context
import androidx.room.Room

class DatabaseFactory {

    fun create(context: Context): CarsDatabase {
        return Room.databaseBuilder(context, CarsDatabase::class.java, "cars_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}