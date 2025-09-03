package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceVendorModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.SupportedDeviceModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model.FlightSheetConfigurationUiState

internal class FlightSheetConfigurationUiStatePreviewParameterProvider : PreviewParameterProvider<FlightSheetConfigurationUiState> {
    private val miners = listOf(
        DeviceMinerModel(
            id = "",
            name = "gminer",
            version = "1.0.0",
            supportedDevices = listOf(
                SupportedDeviceModel(
                    type = DeviceTypeModel.Cpu,
                    vendor = DeviceVendorModel.Amd
                ),
                SupportedDeviceModel(
                    type = DeviceTypeModel.Gpu,
                    vendor = DeviceVendorModel.Nvidia
                )
            ),
            supportedMiningModes = listOf(MiningModeModel.Single, MiningModeModel.Dual)
        )
    )

    override val values: Sequence<FlightSheetConfigurationUiState> = sequenceOf(
        FlightSheetConfigurationUiState(
            minersIsLoading = false,
            miners = miners,
            flightSheet = FlightSheetInputModel(
                targetInputs = listOf(
                    FlightSheetTargetInputModel.CpuTargetInputModel(
                        selectedMiner = miners.first(),
                        isMinerValid = true
                    ),
                    FlightSheetTargetInputModel.GpuTargetInputModel(
                        selectedMiner = miners.first(),
                        isMinerValid = true
                    )
                )
            )
        )
    )
}