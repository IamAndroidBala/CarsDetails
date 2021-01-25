package com.sevenpeakssoftware.carsdetails.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

fun createStringNetworkBoundResource(
    createCall: () -> Single<String>,
    loadFromDB: () -> Flowable<String>,
    saveCallResult: (String) -> Unit): NetworkBoundResource<String, String, String> {

    return object : NetworkBoundResource<String, String, String>(Schedulers.trampoline(), Schedulers.trampoline()) {
        override fun createCall(): Single<String> {
            return createCall()
        }

        override fun loadFromDB(): Flowable<String> {
            return loadFromDB()
        }

        override fun saveCallResult(data: String) {
            saveCallResult(data)
        }

        override fun remoteToDatabaseMapper(): Function<String, String> {
            return Function { it }
        }

        override fun databaseToRepositoryMapper(): Function<String, String> {
            return Function { it }
        }
    }
}