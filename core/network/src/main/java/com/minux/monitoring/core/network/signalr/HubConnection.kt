package com.minux.monitoring.core.network.signalr

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

internal inline fun <reified T> HubConnection.onReceive(method: String) = callbackFlow<Result<T>> {
    val compositeDisposable = CompositeDisposable()

    val subscription = on(method, {
        trySend(Result.success(it))
    }, T::class.java)

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
}.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST).flowOn(Dispatchers.IO)

internal fun <T> HubConnection.onSend(method: String, data: T) = callbackFlow<Result<Unit>> {
    val compositeDisposable = CompositeDisposable()

    fun tryInvoke(): Disposable {
        return invoke(method, data)
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
}.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST).flowOn(Dispatchers.IO)