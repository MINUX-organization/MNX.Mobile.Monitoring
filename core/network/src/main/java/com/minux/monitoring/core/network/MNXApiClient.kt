package com.minux.monitoring.core.network

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionState
import com.minux.monitoring.core.network.api.Receive
import com.minux.monitoring.core.network.api.Send
import io.reactivex.rxjava3.disposables.CompositeDisposable
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
                    if (hubMethodName.isNullOrEmpty()) {
                        onSend(method.name)
                    } else {
                        onSend(hubMethodName)
                    }
                }
            }
        }
    }

    private fun onReceive(hubMethodName: String) = callbackFlow {
        val compositeDisposable = CompositeDisposable()

        val subscription = hubConnection.on(
            hubMethodName,
            {
                trySend(it)
            },
            String::class.java
        )

        val connection = hubConnection.start()
        val disposable = connection.doOnError {
            hubConnection.remove(hubMethodName)
        }.subscribe()

        compositeDisposable.add(disposable)

        awaitClose {
            hubConnection.stop()
            subscription.unsubscribe()
            compositeDisposable.dispose()
        }
    }

    private fun onSend(hubMethodName: String, vararg data: Any) = callbackFlow<Unit> {
        val compositeDisposable = CompositeDisposable()

        when (hubConnection.connectionState!!) {
            HubConnectionState.CONNECTED -> {
                hubConnection.invoke(hubMethodName, data)
            }

            HubConnectionState.DISCONNECTED -> {
                val connection = hubConnection.start()
                val disposable = connection.doOnComplete {
                    hubConnection.invoke(hubMethodName, data)
                }.doOnError {
                    hubConnection.remove(hubMethodName)
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
}