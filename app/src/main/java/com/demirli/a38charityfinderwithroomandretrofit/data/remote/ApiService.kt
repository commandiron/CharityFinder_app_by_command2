package com.demirli.a38charityfinderwithroomandretrofit.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.demirli.a38charityfinderwithroomandretrofit.model.ResponseFromApi

interface ApiService {

    @GET("api/public/services/search/projects.json?api_key=ed980877-85af-4180-b56f-6e2f42663627")
    fun getProjects(@Query("q") searchString: String,
                    @Query("filter") isoCodeString: String): Call<ResponseFromApi.SearchResponse>

}