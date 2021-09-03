package com.demirli.a38charityfinderwithroomandretrofit.data.remote

import com.demirli.a38charityfinderwithroomandretrofit.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val url = originalHttpUrl.newBuilder()
            .build()

        val request = originalRequest.newBuilder().url(url).build()

        return chain.proceed(request)
    }

}