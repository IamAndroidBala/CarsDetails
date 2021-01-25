package com.sevenpeakssoftware.carsdetails.data.network.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.*

class RetrofitFactory {

    fun create(baseUrl: String, loggingEnable: Boolean): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (loggingEnable) addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(
                CarsWebserviceResponseConverterFactory(GsonBuilder().registerTypeAdapter(Date::class.java, DateTypeAdapter("dd.MM.yyyy HH:mm", TimeZone.getTimeZone("UTC"))).create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}