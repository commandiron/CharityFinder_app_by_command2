package com.demirli.a38charityfinderwithroomandretrofit.data.remote

import com.demirli.a38charityfinderwithroomandretrofit.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getApiService(): ApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOKHttpClient())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun getOKHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
        client.addInterceptor(RequestInterceptor())
        return client.build()
    }
}