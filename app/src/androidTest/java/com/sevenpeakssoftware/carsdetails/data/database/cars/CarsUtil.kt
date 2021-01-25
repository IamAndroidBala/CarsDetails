package com.sevenpeakssoftware.carsdetails.data.database.cars

import java.util.*

fun createCars(
    id: Long = 0,
    title: String = "",
    ingress: String = "",
    image: String = "",
    date: Date = Date()): DatabaseCars {
    return DatabaseCars(id, title, ingress, image, date)
}

fun createCars(
    size: Int,
    title: String = "",
    ingress: String = "",
    image: String = "",
    date: Date = Date()): List<DatabaseCars> {
    return mutableListOf<DatabaseCars>().apply {
        (0 until size).forEach {
            add(
                createCars(
                    id = it.toLong(),
                    title = title + it.toString(),
                    ingress = ingress + it.toString(),
                    image = image + it.toString(),
                    date = Date(date.time + it.toLong())
                )
            )
        }
    }
}