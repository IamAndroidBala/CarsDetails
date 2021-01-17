package com.androidbala.carsdetails.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CarsModel::class], version = 1, exportSchema = false)
@TypeConverters(value = [DateTypeConverter::class])
abstract class CarsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}