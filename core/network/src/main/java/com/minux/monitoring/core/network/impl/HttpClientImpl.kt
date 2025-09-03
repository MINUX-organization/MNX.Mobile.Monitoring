package com.minux.monitoring.core.network.impl

import com.minux.monitoring.core.network.BuildConfig
import com.minux.monitoring.core.network.api.BackendApi
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.impl.retrofit.FlowResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal class HttpClientImpl(private val okHttpClient: OkHttpClient) : HttpClient {

    private val json = Json { classDiscriminator = "\$type" }

    override fun getApiClient(api: BackendApi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_URL + api.value)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(FlowResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}