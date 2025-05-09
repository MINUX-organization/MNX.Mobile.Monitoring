package com.minux.monitoring.feature.presets.impl.data.datasource

import com.minux.monitoring.feature.presets.impl.data.model.PresetChangeInputDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetGroupDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetInputDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceGroupDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PresetApiService {

    @GET("/presets/gpu_groups")
    fun getAllPresetsGroupedByGpus(): Flow<Result<List<PresetGroupDto>>>

    @GET("/presets")
    fun getAllPresets(@Query("gpuName") gpuName: String): Flow<Result<List<PresetDto>>>

    @GET("/presets/{presetId}")
    fun getPreset(@Path("presetId") id: String): Flow<Result<PresetDto>>

    @GET("/presets/{presetId}/devices/supported")
    fun getPresetSupportedDevices(
        @Path("presetId") id: String
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    @GET("/presets/{presetId}/devices/supported")
    fun getPresetAppliedDevices(
        @Path("presetId") id: String
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    @POST("/presets/{presetId}/apply")
    fun applyDevicesForPreset(
        @Path("presetId") id: String,
        @Body devices: List<String>
    ): Flow<Result<List<String>>>

    @POST("/presets")
    fun addPreset(@Body input: PresetInputDto): Flow<Result<PresetDto>>

    @PATCH("/presets/{id}")
    fun changePreset(
        @Path("id") id: String,
        @Body changingInput: PresetChangeInputDto
    ): Flow<Result<PresetDto>>

    @DELETE("/presets/{id}")
    fun removePreset(@Path("id") id: String): Flow<Result<Unit>>
}