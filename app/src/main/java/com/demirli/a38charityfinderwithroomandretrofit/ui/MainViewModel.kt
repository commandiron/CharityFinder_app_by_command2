package com.demirli.a38charityfinderwithroomandretrofit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demirli.a38charityfinderwithroomandretrofit.model.Country
import com.demirli.a38charityfinderwithroomandretrofit.model.ResponseFromApi
import java.util.ArrayList

class MainViewModel: ViewModel() {

    private val repository: MainRepository by lazy { MainRepository() }

    fun getProjects(searchString: String, isoCodeString: String): LiveData<List<ResponseFromApi.ProjectResults>>? = repository.getProjects(searchString, isoCodeString)

    fun generateCountries(): ArrayList<Country> = repository.generateCountries()

}