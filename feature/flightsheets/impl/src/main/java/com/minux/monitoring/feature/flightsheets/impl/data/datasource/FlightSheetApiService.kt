package com.minux.monitoring.feature.flightsheets.impl.data.datasource

import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetInputDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceGroupDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface FlightSheetApiService {

    @GET("flight_sheets")
    fun getAllFlightSheets(): Flow<Result<List<FlightSheetDto>>>

    @GET("flight_sheets/{id}")
    fun getFlightSheet(@Path("id") id: String): Flow<Result<FlightSheetDto>>

    @GET("flight_sheets/{id}/devices/supported")
    fun getFlightSheetSupportedDevices(
        @Path("id") id: String
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    @GET("flight_sheets/{id}/devices")
    fun getFlightSheetAppliedDevices(
        @Path("id") id: String
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    @POST("flight_sheets/{id}/apply")
    fun applyDevicesForFlightSheet(
        @Path("id") id: String,
        @Body devices: List<String>
    ): Flow<Result<List<String>>>

    @POST("flight_sheets")
    fun addFlightSheet(@Body input: FlightSheetInputDto): Flow<Result<String>>

    @PUT("flight_sheets/{id}")
    fun changeFlightSheet(
        @Path("id") id: String,
        @Body input: FlightSheetInputDto
    ): Flow<Result<Unit>>

    @DELETE("flight_sheets/{id}")
    fun removeFlightSheet(@Path("id") id: String): Flow<Result<Unit>>
}