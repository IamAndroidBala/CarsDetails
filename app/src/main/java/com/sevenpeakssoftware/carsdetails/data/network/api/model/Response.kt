package com.sevenpeakssoftware.carsdetails.data.network.api.model

data class Response<T>(
    val status: ResponseStatus,
    val content: T
)