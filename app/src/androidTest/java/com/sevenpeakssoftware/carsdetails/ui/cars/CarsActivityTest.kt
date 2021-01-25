package com.sevenpeakssoftware.carsdetails.ui.cars

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.mockito.Mockito

@RunWith(value = AndroidJUnit4::class)
class CarsActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule<CarsActivity>(CarsActivity::class.java)
    private val viewModel: CarsViewModel = Mockito.mock(CarsViewModel::class.java)

    @Before
    fun setup() {
        StandAloneContext.loadKoinModules(
            listOf(module {
                viewModel(override = true) { viewModel }
            })
        )
    }


    @Test
    fun testSingleCarItemView() {
        //TODO:
    }

}