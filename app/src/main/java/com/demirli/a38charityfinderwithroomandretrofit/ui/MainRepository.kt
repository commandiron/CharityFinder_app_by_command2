package com.demirli.a38charityfinderwithroomandretrofit.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demirli.a38charityfinderwithroomandretrofit.data.remote.ApiClient
import com.demirli.a38charityfinderwithroomandretrofit.data.remote.ApiService
import com.demirli.a38charityfinderwithroomandretrofit.model.Country
import com.demirli.a38charityfinderwithroomandretrofit.model.ResponseFromApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainRepository {
    private val apiService: ApiService by lazy { ApiClient.getApiService() }

    fun getProjects(searchString: String, isoCodeString: String): LiveData<List<ResponseFromApi.ProjectResults>>? {
        var projectLiveData: MutableLiveData<List<ResponseFromApi.ProjectResults>> = MutableLiveData()

        apiService.getProjects(searchString,isoCodeString).enqueue(object: Callback<ResponseFromApi.SearchResponse>{
            override fun onFailure(call: Call<ResponseFromApi.SearchResponse>, t: Throwable) {
                Log.e("getProjects", t.message)
            }

            override fun onResponse(call: Call<ResponseFromApi.SearchResponse>, response: Response<ResponseFromApi.SearchResponse>) {

                projectLiveData.value = response.body()?.search?.response?.projects?.project

                println("URL: " + call.request().url)
            }
        })

        return projectLiveData
    }

    fun generateCountries(): ArrayList<Country> {

        val countrieNameToCodeMap = hashMapOf<String,String>()
        for(i in Locale.getISOCountries()){
            val l = Locale("en", i)
            countrieNameToCodeMap.put(l.displayCountry, i)
        }

        val locale = Locale.getISOCountries()
        val countries = arrayListOf<Country>()

        countries.add(Country("Select countries the organization serves:"," ", false))

        locale.forEach {
            countries.add(Country(Locale("",it).displayCountry,countrieNameToCodeMap.get(Locale("",it).displayCountry)!!,false))
        }
        return countries
    }
}