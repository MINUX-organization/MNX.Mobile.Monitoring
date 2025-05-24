package com.minux.monitoring.feature.profile.api

import kotlinx.coroutines.flow.Flow

interface ProfileInfoProvider {
    fun getNickName(): Flow<Result<String>>
}