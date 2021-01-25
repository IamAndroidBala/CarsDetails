package com.sevenpeakssoftware.carsdetails.domain.cars

import java.util.*

data class CarsModel(
    val id: Long,
    val title: String,
    val ingress: String,
    val image: String,
    val date: Date
)