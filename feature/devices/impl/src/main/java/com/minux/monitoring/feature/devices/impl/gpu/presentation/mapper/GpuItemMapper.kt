package com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper

import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuDto
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuItemModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuMiningInfoModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuSoftwareVersionsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuSummaryModel

internal fun GpuDto.toGpuItemModel(): GpuItemModel {
    return GpuItemModel(
        id = id,
        summary = GpuSummaryModel(
            identification = pci.toGpuIdentificationModel(),
            name = DeviceNameModel(
                deviceName = information?.name,
                flightSheetName = flightSheetName,
                presetName = presetName,
                rigName = rigName
            ),
            isOnline = isOnline
        ),
        indicators = GpuIndicatorsModel(),
        coins = emptyList(),
        miningInfo = GpuMiningInfoModel(minerName = minerName),
        specifications = information.toGpuSpecificationsModel(),
        softwareVersions = GpuSoftwareVersionsModel(
            driver = driverVersion,
            technologyType = information?.technology?.type?.toString(),
            technologyVersion = information?.technology?.version,
            vBIOS = information?.biosVersion
        )
    )
}