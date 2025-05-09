package com.minux.monitoring.feature.presets.impl.presentation.mapper

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceRestrictionDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceRestrictionsDto
import com.minux.monitoring.feature.presets.impl.data.model.device.GpuTuningRestrictionDto
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuTuningParameterModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuTuningParametersModel

internal fun DeviceOverclockingDto.toDeviceParametersModel(): DeviceParametersModel {
    return when (this) {
        is DeviceOverclockingDto.CpuOverclockingDto -> { DeviceParametersModel.CpuParametersModel }

        is DeviceOverclockingDto.GpuOverclockingDto -> {
            DeviceParametersModel.GpuParametersModel(
                clocking = GpuTuningParametersModel(
                    coreLock = coreClockLock.toGpuTuningParameterModel(),
                    coreOffset = coreClockOffset.toGpuTuningParameterModel(),
                    memoryLock = memoryClockLock.toGpuTuningParameterModel(),
                    memoryOffset = memoryClockOffset.toGpuTuningParameterModel()
                ),
                voltage = GpuTuningParametersModel(
                    coreLock = coreVoltage.toGpuTuningParameterModel(),
                    coreOffset = coreVoltageOffset.toGpuTuningParameterModel(),
                    memoryLock = memoryVoltage.toGpuTuningParameterModel(),
                    memoryOffset = memoryVoltageOffset.toGpuTuningParameterModel()
                ),
                other = GpuOtherParametersModel(
                    powerLimit = powerLimit.toGpuTuningParameterModel(),
                    fanSpeed = fanSpeed.toGpuTuningParameterModel()
                )
            )
        }
    }
}

internal fun DeviceOverclockingDto.toDeviceParametersModel(
    restrictions: DeviceRestrictionsDto
): DeviceParametersModel? {
    return when {
        this is DeviceOverclockingDto.CpuOverclockingDto -> { DeviceParametersModel.CpuParametersModel }

        this is DeviceOverclockingDto.GpuOverclockingDto && restrictions is DeviceRestrictionsDto.GpuRestrictionsDto -> {
            DeviceParametersModel.GpuParametersModel(
                clocking = restrictions.clock.toGpuTuningParametersModel(
                    coreLockValue = coreClockLock,
                    coreOffsetValue = coreClockOffset,
                    memoryLockValue = memoryClockLock,
                    memoryOffsetValue = memoryClockOffset
                ),
                voltage = restrictions.voltage.toGpuTuningParametersModel(
                    coreLockValue = coreVoltage,
                    coreOffsetValue = coreVoltageOffset,
                    memoryLockValue = memoryVoltage,
                    memoryOffsetValue = memoryVoltageOffset
                ),
                other = GpuOtherParametersModel(
                    powerLimit = restrictions.power.toGpuTuningParameterModel(parameterValue = powerLimit),
                    fanSpeed = restrictions.fanSpeed.toGpuTuningParameterModel(parameterValue = fanSpeed)
                )
            )
        }

        else -> null
    }
}

internal fun DeviceParametersModel.toDeviceOverclockingDto(): DeviceOverclockingDto {
    return when (this) {
        DeviceParametersModel.CpuParametersModel -> { DeviceOverclockingDto.CpuOverclockingDto() }

        is DeviceParametersModel.GpuParametersModel -> {
            DeviceOverclockingDto.GpuOverclockingDto(
                powerLimit = other.powerLimit.toValue(),
                fanSpeed = other.fanSpeed.toValue(),
                coreClockLock = clocking.coreLock.toValue(),
                coreClockOffset = clocking.coreOffset.toValue(),
                memoryClockLock = clocking.memoryLock.toValue(),
                memoryClockOffset = clocking.memoryOffset.toValue(),
                coreVoltage = voltage.coreLock.toValue(),
                coreVoltageOffset = voltage.coreOffset.toValue(),
                memoryVoltage = voltage.memoryLock.toValue(),
                memoryVoltageOffset = voltage.memoryOffset.toValue()
            )
        }
    }
}

private fun GpuTuningRestrictionDto?.toGpuTuningParametersModel(
    coreLockValue: Int?,
    coreOffsetValue: Int?,
    memoryLockValue: Int?,
    memoryOffsetValue: Int?
): GpuTuningParametersModel {
    return GpuTuningParametersModel(
        coreLock = this?.core?.lock?.toGpuTuningParameterModel(parameterValue = coreLockValue),
        coreOffset = this?.core?.offset?.toGpuTuningParameterModel(parameterValue = coreOffsetValue),
        memoryLock = this?.memory?.lock?.toGpuTuningParameterModel(parameterValue = memoryLockValue),
        memoryOffset = this?.memory?.offset?.toGpuTuningParameterModel(parameterValue = memoryOffsetValue)
    )
}

private fun DeviceRestrictionDto?.toGpuTuningParameterModel(parameterValue: Int?): GpuTuningParameterModel? {
    return this?.run {
        GpuTuningParameterModel(
            value = parameterValue?.toFloat() ?: minimal.toFloat(),
            default = default?.toFloat(),
            rangeRestriction = minimal.toFloat()..maximal.toFloat(),
            isWritable = isWritable
        )
    }
}

private fun Int?.toGpuTuningParameterModel(): GpuTuningParameterModel? {
    return this?.let {
        GpuTuningParameterModel(
            value = it.toFloat(),
            default = null,
            rangeRestriction = 0f..0f,
            isWritable = false
        )
    }
}

private fun GpuTuningParameterModel?.toValue(): Int? = this?.value?.toInt()