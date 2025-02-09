package com.minux.monitoring.core.network.impl

import com.minux.monitoring.core.network.BuildConfig
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.impl.retrofit.FlowResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal class HttpClientImpl(private val okHttpClient: OkHttpClient) : HttpClient {

    override fun getApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(FlowResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}