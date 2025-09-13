package com.minux.monitoring.feature.rigs.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class RigDto(
    val id: String,
    val ownerId: String,
    val name: String?,
    val currentInventoryId: Int? = null,
    val isOnline: Boolean,
    @SerialName("lifeCycleStatus") val powerLifecycleStatus: RigLifecycleStatusDto,
    @SerialName("miningLifeCycleStatus") val miningLifecycleStatus: RigLifecycleStatusDto
)