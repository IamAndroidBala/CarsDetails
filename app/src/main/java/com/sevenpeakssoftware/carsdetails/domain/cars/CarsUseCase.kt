package com.sevenpeakssoftware.carsdetails.domain.cars

import com.sevenpeakssoftware.carsdetails.data.repository.Resource
import com.sevenpeakssoftware.carsdetails.data.repository.CarsRepository
import io.reactivex.Flowable
import com.sevenpeakssoftware.carsdetails.data.repository.RepositoryCars as RepositoryArticle

class CarsUseCase(private val repository: CarsRepository) {

    fun get(): Flowable<Resource<List<CarsModel>>> {
        return repository.getCars()
            .map { resource ->
                Resource(
                    resource.status,
                    resource.data?.map { it.toDomainArticle() },
                    resource.error
                )
            }
    }

    private fun RepositoryArticle.toDomainArticle(): CarsModel {
        return CarsModel(id, title, ingress, image, date)
    }

}