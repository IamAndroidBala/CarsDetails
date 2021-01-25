package com.sevenpeakssoftware.carsdetails.data.database.cars

import androidx.test.runner.AndroidJUnit4
import com.sevenpeakssoftware.carsdetails.data.database.DatabaseTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(value = AndroidJUnit4::class)
class CarsDaoTest : DatabaseTest() {

    @Test
    fun insertOneAndRead() {
        val car = createCars(
            id = 1L,
            title = "title",
            ingress = "ingress",
            image = "image",
            date = Date(123456)
        )
        db.carsDao().insertAll(listOf(car))
        val cars = db.carsDao().getAll().blockingFirst()
        assertThat(cars.size, `is`(1))
        assertThat(cars.first().id, `is`(1L))
        assertThat(cars.first().title, `is`("title"))
        assertThat(cars.first().ingress, `is`("ingress"))
        assertThat(cars.first().image, `is`("image"))
        assertThat(cars.first().date, `is`(Date(123456)))
    }

    @Test
    fun insertAllAndDeleteAll() {
        db.carsDao().insertAll(createCars(10, title = "brr"))
        db.carsDao().getAll().blockingFirst().apply {
            assertThat(size, `is`(10))
            forEach { assertThat(it.title, startsWith("brr")) }
        }
        db.carsDao().deleteAll()
        db.carsDao().getAll().blockingFirst().apply {
            assertThat(isEmpty(), `is`(true))
        }
    }

    @Test
    fun insertAllAndUpdateAll() {
        db.carsDao().insertAll(createCars(10, title = "first"))
        db.carsDao().updateData(createCars(15, title = "second"))
        db.carsDao().getAll().blockingFirst().apply {
            assertThat(size, `is`(15))
            forEach { assertThat(it.title, startsWith("second")) }
        }
    }

}