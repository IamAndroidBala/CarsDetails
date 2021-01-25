package com.sevenpeakssoftware.carsdetails.ui.cars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenpeakssoftware.carsdetails.databinding.ActivityCarsBinding
import com.sevenpeakssoftware.carsdetails.utils.showToastMessage
import com.sevenpeakssoftware.carsdetails.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarsActivity : AppCompatActivity() {

    private val bindingCar by viewBinding(ActivityCarsBinding::inflate)

    private val viewModel by viewModel<CarsViewModel>()
    private var carsAdapter = CarsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(bindingCar.root)

        bindingCar.recyclerCars.layoutManager = LinearLayoutManager(this)
        bindingCar.recyclerCars.adapter = carsAdapter

        viewModel.cars.observe(this, Observer {
            carsAdapter.submitList(it)
        })

        viewModel.errorEvents.observe(this, Observer {
            it?.data?.let { errorMessage ->
                showToastMessage(this@CarsActivity, errorMessage)
            }
        })

        viewModel.loadingStatus.observe(this, Observer {
            //TODO: show loading
        })

    }

}