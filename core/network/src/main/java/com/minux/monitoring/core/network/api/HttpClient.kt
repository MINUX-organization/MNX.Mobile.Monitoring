package com.minux.monitoring.core.network.api

import retrofit2.Retrofit

interface HttpClient {
    fun getApiClient(): Retrofit
}