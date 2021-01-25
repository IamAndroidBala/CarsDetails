package com.sevenpeakssoftware.carsdetails.data.database.cars

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DatabaseCars(
    @PrimaryKey
    val id: Long,
    val title: String,
    val ingress: String,
    val image: String,
    val date: Date
)