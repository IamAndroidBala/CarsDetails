package com.androidbala.carsdetails.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class CarsModel(
    @PrimaryKey
    val id: Long,
    val title: String,
    val ingress: String,
    val image: String,
    val date: Date
)