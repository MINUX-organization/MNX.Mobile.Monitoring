package com.minux.monitoring.core.network.api

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionState
import com.microsoft.signalr.Subscription
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Proxy
import java.lang.reflect.Type

inline fun <reified T> HubConnection.onReceive(
    crossinline callback: ProducerScope<Result<T>>.(connection: HubConnection) -> Subscription
) = callbackFlow<Result<T>> {
    val compositeDisposable = CompositeDisposable()

    val subscription = callback(this@onReceive)

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

    fun tryInvoke(): Disposable {
        return invoke(hubMethodName, data)
            .doOnComplete {
                trySend(Result.success(Unit))
            }
            .doOnError {
                trySend(Result.failure(it))
            }
            .subscribe()
    }

    when (connectionState!!) {
        HubConnectionState.CONNECTED -> {
            val invokeDisposable = tryInvoke()
            compositeDisposable.add(invokeDisposable)
        }

        HubConnectionState.DISCONNECTED -> {
            val connection = start()
            val disposable = connection.doOnComplete {
                val invokeDisposable = tryInvoke()
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