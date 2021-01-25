package com.sevenpeakssoftware.carsdetails.data.repository

import com.sevenpeakssoftware.carsdetails.data.database.cars.CarsDao
import com.sevenpeakssoftware.carsdetails.data.database.cars.DatabaseCars
import com.sevenpeakssoftware.carsdetails.data.network.api.api.cars.CarsApi
import com.sevenpeakssoftware.carsdetails.data.network.api.api.cars.NetworkCars
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class CarsRepository(private val api: CarsApi, private val dao: CarsDao) {

    fun getCars(): Flowable<Resource<List<RepositoryCars>>> {
        return object : NetworkBoundResource<List<DatabaseCars>, List<NetworkCars>, List<RepositoryCars>>(
            remoteScheduler = Schedulers.newThread(),
            databaseScheduler = Schedulers.newThread()
        ) {
            override fun createCall(): Single<List<NetworkCars>> {
                return api.getCarsList()
            }

            override fun loadFromDB(): Flowable<List<DatabaseCars>> {
                return dao.getAll().distinctUntilChanged()
            }

            override fun saveCallResult(data: List<DatabaseCars>) {
                dao.updateData(data)
            }

            override fun remoteToDatabaseMapper(): Function<List<NetworkCars>, List<DatabaseCars>> {
                return Function { networkCars -> networkCars.map { it.toDatabaseCars() } }
            }

            override fun databaseToRepositoryMapper(): Function<List<DatabaseCars>, List<RepositoryCars>> {
                return Function { databaseCars -> databaseCars.map { it.toRepositoryCars() } }
            }

        }.toFlowable()
    }

    private fun NetworkCars.toDatabaseCars(): DatabaseCars {
        return DatabaseCars(
            id = id,
            title = title,
            ingress = ingress,
            image = image,
            date = dateTime
        )
    }

    private fun DatabaseCars.toRepositoryCars(): RepositoryCars {
        return RepositoryCars(
            id,
            title,
            ingress,
            image,
            date
        )
    }
}
