package com.sevenpeakssoftware.carsdetails.data.network.api

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit

abstract class CarsWebserviceTest {

    lateinit var webServer: MockWebServer
    lateinit var retorfit: Retrofit

    @Before
    fun setup() {
        webServer = MockWebServer()
        webServer.start(8080)
        retorfit = RetrofitFactory().create("http://localhost:8080/", true)
    }

    @After
    fun tearDown() {
        webServer.shutdown()
    }
}