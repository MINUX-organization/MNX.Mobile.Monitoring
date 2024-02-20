package com.minux.monitoring.core.network

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionState
import com.minux.monitoring.core.network.api.Receive
import com.minux.monitoring.core.network.api.Send
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class MNXApiClient(private val hubConnection: HubConnection) {
    init {
        val methods = MNXApiService::class.java.methods
        methods.forEach { method ->
             when {
                method.isAnnotationPresent(Receive::class.java) -> {
                    val hubMethodName = method.getAnnotation(Receive::class.java)?.value
                    val callbackFlow = if (hubMethodName.isNullOrEmpty()) {
                        onReceive(method.name)
                    } else {
                        onReceive(hubMethodName)
                    }
                    method.invoke(callbackFlow)
                }

                method.isAnnotationPresent(Send::class.java) -> {
                    val hubMethodName = method.getAnnotation(Send::class.java)?.value
                    val callbackFlow = if (hubMethodName.isNullOrEmpty()) {
                        onSend(method.name)
                    } else {
                        onSend(hubMethodName)
                    }
                    method.invoke(callbackFlow)
                }
            }
        }
    }

    private fun onReceive(hubMethodName: String) = callbackFlow<Result<String>> {
        val compositeDisposable = CompositeDisposable()

        val subscription = hubConnection.on(
            hubMethodName,
            {
                trySend(Result.success(it))
            },
            String::class.java
        )

        val connection = hubConnection.start()
        val disposable = connection.doOnError {
            trySend(Result.failure(it))
        }.subscribe()

        compositeDisposable.add(disposable)

        awaitClose {
            hubConnection.stop()
            subscription.unsubscribe()
            compositeDisposable.dispose()
        }
    }

    private fun onSend(hubMethodName: String, vararg data: Any) = callbackFlow<Result<Unit>> {
        val compositeDisposable = CompositeDisposable()

        when (hubConnection.connectionState!!) {
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
                val connection = hubConnection.start()
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
            hubConnection.stop()
            compositeDisposable.dispose()
        }
    }

    private fun tryInvoke(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit,
        hubMethodName: String,
        vararg data: Any,
    ): Disposable {
        return hubConnection
            .invoke(hubMethodName, data)
            .doOnComplete(onComplete)
            .doOnError(onError)
            .subscribe()
    }
}