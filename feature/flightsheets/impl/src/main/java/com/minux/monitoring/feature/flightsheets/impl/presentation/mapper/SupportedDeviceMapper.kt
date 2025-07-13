package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner.SupportedDeviceDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceVendorModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.SupportedDeviceModel

internal fun SupportedDeviceDto.toSupportedDeviceModel(): SupportedDeviceModel? = when(this) {
    SupportedDeviceDto.NvidiaGpu -> SupportedDeviceModel(
        type = DeviceTypeModel.Gpu,
        vendor = DeviceVendorModel.Nvidia
    )

    SupportedDeviceDto.IntelGpu -> SupportedDeviceModel(
        type = DeviceTypeModel.Gpu,
        vendor = DeviceVendorModel.Intel
    )

    SupportedDeviceDto.IntelCpu -> SupportedDeviceModel(
        type = DeviceTypeModel.Cpu,
        vendor = DeviceVendorModel.Intel
    )

    SupportedDeviceDto.AmdGpu -> SupportedDeviceModel(
        type = DeviceTypeModel.Gpu,
        vendor = DeviceVendorModel.Amd
    )

    SupportedDeviceDto.AmdCpu -> SupportedDeviceModel(
        type = DeviceTypeModel.Cpu,
        vendor = DeviceVendorModel.Amd
    )

    SupportedDeviceDto.None -> null
}