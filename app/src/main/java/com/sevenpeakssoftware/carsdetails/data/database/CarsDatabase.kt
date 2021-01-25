package com.sevenpeakssoftware.carsdetails.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sevenpeakssoftware.carsdetails.data.database.cars.DatabaseCars
import com.sevenpeakssoftware.carsdetails.data.database.cars.CarsDao

@Database(entities = [DatabaseCars::class], version = 1, exportSchema = false)
@TypeConverters(value = [DateTypeConverter::class])
abstract class CarsDatabase : RoomDatabase() {
    abstract fun carsDao(): CarsDao
}