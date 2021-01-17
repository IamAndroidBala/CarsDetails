package com.androidbala.carsdetails.data.db

import androidx.room.*
import io.reactivex.Flowable

@Dao
abstract class ArticleDao {

    @Query("SELECT * FROM CarsModel")
    abstract fun getAll(): Flowable<List<CarsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(articles: List<CarsModel>)

    @Query("DELETE FROM CarsModel")
    abstract fun deleteAll()

    @Transaction
    open fun updateData(articles: List<CarsModel>) {
        deleteAll()
        return insertAll(articles)
    }
}