package com.minux.monitoring.feature.auth.impl.data.repository

import com.minux.monitoring.feature.auth.impl.data.model.AuthInfoDto
import kotlinx.coroutines.flow.Flow

internal interface AuthRepository {
    fun registerUser(authInfo: AuthInfoDto): Flow<Result<Unit>>

    fun authUser(authInfo: AuthInfoDto): Flow<Result<Unit>>
}