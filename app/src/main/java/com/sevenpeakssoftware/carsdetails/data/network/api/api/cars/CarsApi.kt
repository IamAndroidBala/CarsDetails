package com.sevenpeakssoftware.carsdetails.data.network.api.api.cars

import io.reactivex.Single
import retrofit2.http.GET

interface CarsApi {

    @GET("article/get_articles_list")
    fun getCarsList(): Single<List<NetworkCars>>

}