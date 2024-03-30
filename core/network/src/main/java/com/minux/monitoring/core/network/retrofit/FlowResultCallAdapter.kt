package com.minux.monitoring.core.network.retrofit

import com.minux.monitoring.core.network.model.exception.AppExceptionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

internal class FlowResultCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Flow<Result<T>>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<T>) = callbackFlow {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    trySend(
                        Result.success(value = response.body() ?: Unit as T)
                    )
                } else {
                    when (response.code()) {
                        400 -> trySend(
                            Result.failure(
                                exception = AppExceptionDto.BadRequestExceptionDto(
                                    data = response.errorBody() as List<String>
                                )
                            )
                        )
                        401 -> trySend(
                            Result.failure(exception = AppExceptionDto.UnauthorizedExceptionDto)
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                trySend(
                    Result.failure(exception = t)
                )
            }
        })

        awaitClose {
            call.cancel()
        }
    }.flowOn(Dispatchers.IO)
}