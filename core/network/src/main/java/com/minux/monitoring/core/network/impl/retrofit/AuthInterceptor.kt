package com.minux.monitoring.core.network.impl.retrofit

import com.minux.monitoring.core.network.api.request.WithoutAuth
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.impl.session.SessionManagerImpl
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

internal class AuthInterceptor(private val lazySessionManager: Lazy<SessionManager>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val invocation = originalRequest.tag(Invocation::class.java)

        if (invocation?.method()?.getAnnotation(WithoutAuth::class.java) == null) {
            val accessToken = runBlocking {
                (lazySessionManager.get() as SessionManagerImpl).getAccessToken()
            }

            val authorizedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()

            return chain.proceed(request = authorizedRequest)
        }

        return chain.proceed(request = originalRequest)
    }
}