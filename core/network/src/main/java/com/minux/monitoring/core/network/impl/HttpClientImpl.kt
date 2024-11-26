package com.minux.monitoring.core.network.impl

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.impl.retrofit.FlowResultCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class HttpClientImpl(private val okHttpClient: OkHttpClient) : HttpClient {
    private val baseUrl = "https://something.com/api/"

    override fun getApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}