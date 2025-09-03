package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceVendorModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.SupportedDeviceModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetModel

internal class FlightSheetItemPreviewParameterProvider : PreviewParameterProvider<FlightSheetItemModel> {
    private val flightSheetItem = FlightSheetItemModel(
        name = "Sample name #1",
        targets = listOf(
            FlightSheetTargetModel.CpuTargetModel(
                miner = DeviceMinerModel(
                    id = "",
                    name = "gMiner",
                    version = "1.0",
                    supportedDevices = listOf(
                        SupportedDeviceModel(type = DeviceTypeModel.Gpu, vendor = DeviceVendorModel.Amd),
                        SupportedDeviceModel(type = DeviceTypeModel.Gpu, vendor = DeviceVendorModel.Nvidia),
                        SupportedDeviceModel(type = DeviceTypeModel.Cpu, vendor = DeviceVendorModel.Intel)
                    ),
                    supportedMiningModes = emptyList()
                ),
                coinConfigs = listOf(
                    MiningCoinConfigModel(
                        pool = MiningPoolModel(id = "", name = "samplepool.com", cryptocurrency = "ETH"),
                        wallet = MiningWalletModel(id = "", name = "samplewallet", coin = "ETH"),
                        poolPassword = null
                    )
                )
            ),
            FlightSheetTargetModel.GpuTargetModel(
                miner = DeviceMinerModel(
                    id = "",
                    name = "lolMiner",
                    version = "1.0",
                    supportedDevices = listOf(
                        SupportedDeviceModel(type = DeviceTypeModel.Gpu, vendor = DeviceVendorModel.Amd),
                        SupportedDeviceModel(type = DeviceTypeModel.Gpu, vendor = DeviceVendorModel.Nvidia),
                        SupportedDeviceModel(type = DeviceTypeModel.Gpu, vendor = DeviceVendorModel.Intel)
                    ),
                    supportedMiningModes = emptyList()
                ),
                coinConfigs = listOf(
                    MiningCoinConfigModel(
                        pool = MiningPoolModel(id = "", name = "samplepool.com", cryptocurrency = "ETH"),
                        wallet = MiningWalletModel(id = "", name = "samplewallet", coin = "ETH"),
                        poolPassword = null
                    )
                )
            )
        )
    )

    override val values: Sequence<FlightSheetItemModel> = sequenceOf(
        flightSheetItem,
        flightSheetItem.copy(
            targets = listOf(
                FlightSheetTargetModel.CpuTargetModel(
                    miner = DeviceMinerModel(
                        id = "",
                        name = "gMiner",
                        version = "1.0",
                        supportedDevices = listOf(
                            SupportedDeviceModel(type = DeviceTypeModel.Gpu, vendor = DeviceVendorModel.Amd),
                            SupportedDeviceModel(type = DeviceTypeModel.Gpu, vendor = DeviceVendorModel.Nvidia),
                            SupportedDeviceModel(type = DeviceTypeModel.Cpu, vendor = DeviceVendorModel.Intel)
                        ),
                        supportedMiningModes = emptyList()
                    ),
                    coinConfigs = listOf(
                        MiningCoinConfigModel(
                            pool = MiningPoolModel(id = "", name = "samplepool.com", cryptocurrency = "ETH"),
                            wallet = MiningWalletModel(id = "", name = "samplewallet", coin = "ETH"),
                            poolPassword = null
                        )
                    )
                )
            )
        )
    )
}