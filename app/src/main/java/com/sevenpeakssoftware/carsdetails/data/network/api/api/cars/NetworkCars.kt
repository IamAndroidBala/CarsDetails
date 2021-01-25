package com.sevenpeakssoftware.carsdetails.data.network.api.api.cars

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkCars(
    val id: Long,
    val title: String,
    val ingress: String,
    val image: String,
    val tags: List<String>,
    val dateTime: Date,
    val created: Long,
    val changed: Long,
    val content: List<Item>) {

    data class Item(
        val type: Type,
        val subject: String,
        val description: String) {
        enum class Type {
            @SerializedName("text")
            TEXT
        }
    }
}