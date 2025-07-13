package com.minux.monitoring.feature.flightsheets.impl.data.repository

import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetAppliedDevicesGetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetChangeDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetDevicesApplyDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetGetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetInputDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetRemoveDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetSupportedDevicesGetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceGroupDto
import kotlinx.coroutines.flow.Flow

internal interface FlightSheetRepository {
    fun getAllFlightSheets(): Flow<Result<List<FlightSheetDto>>>

    fun getFlightSheet(flightSheetGet: FlightSheetGetDto): Flow<Result<FlightSheetDto>>

    fun getFlightSheetSupportedDevices(
        flightSheetSupportedDevicesGet: FlightSheetSupportedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    fun getFlightSheetAppliedDevices(
        flightSheetAppliedDevicesGet: FlightSheetAppliedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    fun applyDevicesForFlightSheet(
        flightSheetDevicesApply: FlightSheetDevicesApplyDto
    ): Flow<Result<List<String>>>

    fun addFlightSheet(flightSheetInput: FlightSheetInputDto): Flow<Result<String>>

    fun changeFlightSheet(flightSheetChange: FlightSheetChangeDto): Flow<Result<Unit>>

    fun removeFlightSheet(flightSheetRemove: FlightSheetRemoveDto): Flow<Result<Unit>>
}