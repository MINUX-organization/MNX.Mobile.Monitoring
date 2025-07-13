package com.minux.monitoring.feature.flightsheets.impl.data.repository

import com.minux.monitoring.feature.flightsheets.impl.data.datasource.FlightSheetApiService
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

internal class FlightSheetRepositoryImpl(
    private val flightSheetApiService: FlightSheetApiService
) : FlightSheetRepository {

    override fun getAllFlightSheets(): Flow<Result<List<FlightSheetDto>>> {
        return flightSheetApiService.getAllFlightSheets()
    }

    override fun getFlightSheet(flightSheetGet: FlightSheetGetDto): Flow<Result<FlightSheetDto>> {
        return flightSheetApiService.getFlightSheet(id = flightSheetGet.flightSheetId)
    }

    override fun getFlightSheetSupportedDevices(
        flightSheetSupportedDevicesGet: FlightSheetSupportedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>> {
        return flightSheetApiService.getFlightSheetSupportedDevices(
            id = flightSheetSupportedDevicesGet.flightSheetId
        )
    }

    override fun getFlightSheetAppliedDevices(
        flightSheetAppliedDevicesGet: FlightSheetAppliedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>> {
        return flightSheetApiService.getFlightSheetAppliedDevices(
            id = flightSheetAppliedDevicesGet.flightSheetId
        )
    }

    override fun applyDevicesForFlightSheet(
        flightSheetDevicesApply: FlightSheetDevicesApplyDto
    ): Flow<Result<List<String>>> {
        return flightSheetApiService.applyDevicesForFlightSheet(
            id = flightSheetDevicesApply.flightSheetId,
            devices = flightSheetDevicesApply.devices
        )
    }

    override fun addFlightSheet(flightSheetInput: FlightSheetInputDto): Flow<Result<String>> {
        return flightSheetApiService.addFlightSheet(input = flightSheetInput)
    }

    override fun changeFlightSheet(flightSheetChange: FlightSheetChangeDto): Flow<Result<Unit>> {
        return flightSheetApiService.changeFlightSheet(
            id = flightSheetChange.id,
            input = flightSheetChange.input
        )
    }

    override fun removeFlightSheet(flightSheetRemove: FlightSheetRemoveDto): Flow<Result<Unit>> {
        return flightSheetApiService.removeFlightSheet(id = flightSheetRemove.id)
    }
}