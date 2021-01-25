package com.sevenpeakssoftware.carsdetails.ui.cars

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.toLiveData
import com.sevenpeakssoftware.carsdetails.data.repository.Resource
import com.sevenpeakssoftware.carsdetails.domain.cars.CarsModel
import com.sevenpeakssoftware.carsdetails.domain.cars.CarsUseCase
import com.sevenpeakssoftware.carsdetails.testing.OpenForTesting
import com.sevenpeakssoftware.carsdetails.utils.ErrorHandler
import com.sevenpeakssoftware.carsdetails.utils.formatDateTime

@OpenForTesting
class CarsViewModel(application: Application, carsUseCase: CarsUseCase) : AndroidViewModel(application) {

    private val carsResource = carsUseCase.get().toLiveData()

    private val errors = MediatorLiveData<Throwable>().apply {
        addSource(carsResource) { newResource ->
            if (newResource?.error != null && newResource.error !== value) {
                value = newResource.error
            }
        }
    }

    val errorEvents = Transformations.map(errors) {
        ErrorHandler(it.message)
    }

    val loadingStatus = Transformations.map(carsResource) {
        it.status == Resource.Status.LOADING
    }

    val cars = Transformations.map(carsResource) { resource ->
        resource.data?.map { it.toCarsItem() }
    }

    private fun CarsModel.toCarsItem(): com.sevenpeakssoftware.carsdetails.ui.cars.CarsModel {
        return CarsModel(
            id = id,
            title = title,
            ingress = ingress,
            image = image,
            date = formatDateTime(
                getApplication(),
                date
            )
        )
    }
}