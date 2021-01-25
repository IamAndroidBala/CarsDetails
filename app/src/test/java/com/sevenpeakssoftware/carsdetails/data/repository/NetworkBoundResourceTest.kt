package com.sevenpeakssoftware.carsdetails.data.repository

import io.reactivex.BackpressureStrategy
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

@RunWith(value = JUnit4::class)
class NetworkBoundResourceTest {

    @Test
    fun successResponseSavedInEmptyDatabase() {
        val mockDatabase = BehaviorSubject.create<String>()
        val resource = createStringNetworkBoundResource(
            createCall = {
                Single.just("call response")
                    .delay(1, TimeUnit.SECONDS)
            },
            loadFromDB = {
                mockDatabase.toFlowable(BackpressureStrategy.LATEST)
            },
            saveCallResult = {
                mockDatabase.onNext(it)
            }
        )
        val result = resource.toFlowable()
            .filter { it.data != null }
            .map { it.data }
            .blockingFirst()
        assertThat(result, `is`("call response"))
    }

    @Test
    fun successResponseSavedInDatabaseWithData() {
        //TODO
    }

}