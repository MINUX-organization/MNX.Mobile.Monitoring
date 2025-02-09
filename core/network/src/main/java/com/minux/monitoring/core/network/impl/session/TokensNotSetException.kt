package com.minux.monitoring.core.network.impl.session

internal class TokensNotSetException : IllegalStateException(
    "Tokens are not set. Please ensure tokens are initialized before accessing them."
)