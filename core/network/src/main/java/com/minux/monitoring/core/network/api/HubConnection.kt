package com.minux.monitoring.core.network.api

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Proxy
import java.lang.reflect.Type

inline fun <reified T> HubConnection.defineApi(service: Class<T>): T {
    if (!service.isInterface) {
        throw IllegalArgumentException("Api declarations must be interfaces.")
    }

    return Proxy.newProxyInstance(service.classLoader, arrayOf(service)) { _, method, args ->
        return@newProxyInstance when {
            method.isAnnotationPresent(Receive::class.java) -> {
                val receiveAnnotation = method.getAnnotation(Receive::class.java)
                val hubMethodName = receiveAnnotation?.value
                if (hubMethodName.isNullOrEmpty()) {
                    onReceive(hubMethodName = method.name)
                } else {
                    onReceive(hubMethodName = hubMethodName)
                }
            }

            method.isAnnotationPresent(Send::class.java) -> {
                val hubMethodName = method.getAnnotation(Send::class.java)?.value
                if (hubMethodName.isNullOrEmpty()) {
                    onSend(method.name, *method.genericParameterTypes)
                } else {
                    onSend(hubMethodName, *method.genericParameterTypes)
                }
            }

            else -> {
                method.invoke(this, args)
            }
        }
    } as T
}

fun HubConnection.onReceive(hubMethodName: String) = callbackFlow<Result<String>> {
    val compositeDisposable = CompositeDisposable()

    val subscription = on(
        hubMethodName,
        {
            trySend(Result.success(it))
        },
        String::class.java
    )

    val connection = start()
    val disposable = connection.doOnError {
        trySend(Result.failure(it))
    }.subscribe()

    compositeDisposable.add(disposable)

    awaitClose {
        stop()
        subscription.unsubscribe()
        compositeDisposable.dispose()
    }
}

fun HubConnection.onSend(hubMethodName: String, vararg data: Type) = callbackFlow<Result<Unit>> {
    val compositeDisposable = CompositeDisposable()

    when (connectionState!!) {
        HubConnectionState.CONNECTED -> {
            val invokeDisposable = tryInvoke(
                onComplete = {
                    trySend(Result.success(Unit))
                },
                onError = {
                    trySend(Result.failure(it))
                },
                hubMethodName = hubMethodName,
                data = data
            )
            compositeDisposable.add(invokeDisposable)
        }

        HubConnectionState.DISCONNECTED -> {
            val connection = start()
            val disposable = connection.doOnComplete {
                val invokeDisposable = tryInvoke(
                    onComplete = {
                        trySend(Result.success(Unit))
                    },
                    onError = {
                        trySend(Result.failure(it))
                    },
                    hubMethodName = hubMethodName,
                    data = data
                )
                compositeDisposable.add(invokeDisposable)
            }.doOnError {
                trySend(Result.failure(it))
            }.subscribe()

            compositeDisposable.add(disposable)
        }

        else -> {}
    }

    awaitClose {
        stop()
        compositeDisposable.dispose()
    }
}

private fun HubConnection.tryInvoke(
    onComplete: () -> Unit,
    onError: (Throwable) -> Unit,
    hubMethodName: String,
    vararg data: Type,
): Disposable {
    return this
        .invoke(hubMethodName, data)
        .doOnComplete(onComplete)
        .doOnError(onError)
        .subscribe()
}