package com.sevenpeakssoftware.carsdetails.data.database.cars

import androidx.room.*
import io.reactivex.Flowable

@Dao
abstract class CarsDao {

    @Query("SELECT * FROM DatabaseCars")
    abstract fun getAll(): Flowable<List<DatabaseCars>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(cars: List<DatabaseCars>)

    @Query("DELETE FROM DatabaseCars")
    abstract fun deleteAll()

    @Transaction
    open fun updateData(cars: List<DatabaseCars>) {
        deleteAll()
        return insertAll(cars)
    }
}