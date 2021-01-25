package com.sevenpeakssoftware.carsdetails.data.network.api.api.cars

import com.sevenpeakssoftware.carsdetails.data.network.api.CarsWebserviceTest
import com.sevenpeakssoftware.carsdetails.data.network.api.exception.ApiException
import junit.framework.Assert.fail
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import java.util.*

@RunWith(value = JUnit4::class)
class CarApiTest : CarsWebserviceTest() {

    lateinit var carApi: CarsApi

    @Before
    fun createArticleApi() {
        carApi = retorfit.create(CarsApi::class.java)
    }

    @Test
    fun getArticlesListSuccessfully() {
        //TODO: move mock body to file
        webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    "{\n" +
                            "  \"status\": \"success\",\n" +
                            "  \"content\": [\n" +
                            "    {\n" +
                            "      \"id\": 119302,\n" +
                            "      \"title\": \"Q7 - Greatness starts, when you don't stop.\",\n" +
                            "      \"dateTime\": \"25.05.2018 14:13\",\n" +
                            "      \"tags\": [\n" +
                            "        \n" +
                            "      ],\n" +
                            "      \"content\": [\n" +
                            "        {\n" +
                            "          \"type\": \"text\",\n" +
                            "          \"subject\": \"Q7\",\n" +
                            "          \"description\": \"The Audi Q7 is masculine, yet exudes lightness. Inside, it offers comfort at the highest level. With even more space for your imagination. The 3.0 TDI engine accelerates this powerhouse as a five-seater starting at an impressive 6.3 seconds from 0 to 100 km\\/h.\"\n" +
                            "        }\n" +
                            "      ],\n" +
                            "      \"ingress\": \"The Audi Q7 is the result of an ambitious idea: never cease to improve.\",\n" +
                            "      \"image\": \"https:\\/\\/www.apphusetreach.no\\/sites\\/default\\/files\\/audi_q7.jpg\",\n" +
                            "      \"created\": 1511968425,\n" +
                            "      \"changed\": 1534311497\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": 119300,\n" +
                            "      \"title\": \"Q5 - Created for almost any landscape.\",\n" +
                            "      \"dateTime\": \"29.11.2017 15:12\",\n" +
                            "      \"tags\": [\n" +
                            "        \n" +
                            "      ],\n" +
                            "      \"content\": [\n" +
                            "        {\n" +
                            "          \"type\": \"text\",\n" +
                            "          \"subject\": \"Q5\",\n" +
                            "          \"description\": \"Expressive appearance, powerful drive, and pioneering technology. Your options are just as diverse. Every day anew \\u2013 with the Audi Q5. Don't leave anything to chance. Whether it comes to comfort, style, or dynamics, the Audi Q5 will win you over from the first moment. \"\n" +
                            "        }\n" +
                            "      ],\n" +
                            "      \"ingress\": \"Don't leave anything to chance. Whether it comes to comfort, style, or dynamics, the Audi Q5 will win you over from the first moment. \",\n" +
                            "      \"image\": \"https:\\/\\/www.apphusetreach.no\\/sites\\/default\\/files\\/audi_q5.jpg\",\n" +
                            "      \"created\": 1511968397,\n" +
                            "      \"changed\": 1516864387\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"serverTime\": 1552478705\n" +
                            "}"
                )
        )
        carApi.getCarsList().blockingGet().apply {
            assertThat(size, `is`(2))
            assertThat(first().id, `is`(119302L))
            assertThat(first().title, `is`("Q7 - Greatness starts, when you don't stop."))
            assertThat(first().ingress, `is`("The Audi Q7 is the result of an ambitious idea: never cease to improve."))
            assertThat(first().image, `is`("https://www.apphusetreach.no/sites/default/files/audi_q7.jpg"))
            assertThat(first().created, `is`(1511968425L))
            assertThat(first().changed, `is`(1534311497L))
            assertThat(first().content.size, `is`(1))
            assertThat(
                first().content.first().description,
                `is`("The Audi Q7 is masculine, yet exudes lightness. Inside, it offers comfort at the highest level. With even more space for your imagination. The 3.0 TDI engine accelerates this powerhouse as a five-seater starting at an impressive 6.3 seconds from 0 to 100 km/h.")
            )
            assertThat(first().content.first().subject, `is`("Q7"))
            assertThat(first().content.first().type, `is`(NetworkCars.Item.Type.TEXT))
            assertThat(first().tags.isEmpty(), `is`(true))
            val calendar = Calendar.getInstance().apply {
                timeZone = TimeZone.getTimeZone("UTC")
                timeInMillis = first().dateTime.time
            }
            assertThat(calendar.get(Calendar.DAY_OF_MONTH), `is`(25))
            assertThat(calendar.get(Calendar.MONTH) + 1, `is`(5))
            assertThat(calendar.get(Calendar.YEAR), `is`(2018))
            assertThat(calendar.get(Calendar.HOUR_OF_DAY), `is`(14))
            assertThat(calendar.get(Calendar.MINUTE), `is`(13))
        }
    }

    @Test
    fun getArticlesListNetworkError() {
        webServer.enqueue(
            MockResponse()
                .setResponseCode(300)
                .setBody("")
        )
        try {
            carApi.getCarsList().blockingGet()
            fail("request successful")
        } catch (e: HttpException) {
            assertThat(e.code(), `is`(300))
        }
    }

    @Test
    fun getArticleInvalidResponseBodyError() {
        webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{}")
        )
        try {
            carApi.getCarsList().blockingGet()
            fail("request successful")
        } catch (ignore: ApiException) { }
    }
}