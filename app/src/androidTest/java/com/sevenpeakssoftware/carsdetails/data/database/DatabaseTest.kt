package com.sevenpeakssoftware.carsdetails.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before

abstract class DatabaseTest {

    private lateinit var _db: CarsDatabase
    val db: CarsDatabase
        get() = _db

    @Before
    fun initDb() {
        _db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CarsDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        _db.close()
    }
}