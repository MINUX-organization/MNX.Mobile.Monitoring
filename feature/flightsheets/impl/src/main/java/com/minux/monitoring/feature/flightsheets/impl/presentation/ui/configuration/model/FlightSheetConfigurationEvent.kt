package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel
import java.io.File

internal sealed interface FlightSheetConfigurationEvent {
    class FetchFlightSheetParameters(val mode: ConfigurationMode) : FlightSheetConfigurationEvent

    class NameChanged(val name: String) : FlightSheetConfigurationEvent

    class TargetTypeChanged(val targetType: FlightSheetTargetTypeModel) : FlightSheetConfigurationEvent

    class CpuMinerChanged(val miner: DeviceMinerModel?) : FlightSheetConfigurationEvent

    class CpuHugePagesChanged(val hugePages: String) : FlightSheetConfigurationEvent

    class CpuThreadsCountChanged(val threadsCount: String) : FlightSheetConfigurationEvent

    class CpuPoolChanged(
        val pool: MiningPoolModel?,
        val coinConfigIndex: Int
    ) : FlightSheetConfigurationEvent

    class CpuWalletChanged(
        val wallet: MiningWalletModel?,
        val coinConfigIndex: Int
    ) : FlightSheetConfigurationEvent

    class CpuPoolPasswordChanged(
        val poolPassword: String,
        val coinConfigIndex: Int
    ) : FlightSheetConfigurationEvent

    class CpuMinerAdditionalArgumentsChanged(val minerAdditionalArguments: String) : FlightSheetConfigurationEvent

    class CpuMinerConfigFileChanged(val minerConfigFile: File?) : FlightSheetConfigurationEvent

    class GpuMinerChanged(val miner: DeviceMinerModel?) : FlightSheetConfigurationEvent

    class GpuMinerMiningModeChanged(val minerMiningMode: MiningModeModel) : FlightSheetConfigurationEvent

    class GpuPoolChanged(
        val pool: MiningPoolModel?,
        val coinConfigIndex: Int
    ) : FlightSheetConfigurationEvent

    class GpuWalletChanged(
        val wallet: MiningWalletModel?,
        val coinConfigIndex: Int
    ) : FlightSheetConfigurationEvent

    class GpuPoolPasswordChanged(
        val poolPassword: String,
        val coinConfigIndex: Int
    ) : FlightSheetConfigurationEvent

    class GpuMinerAdditionalArgumentsChanged(val minerAdditionalArguments: String) : FlightSheetConfigurationEvent

    class GpuMinerConfigFileChanged(val minerConfigFile: File?) : FlightSheetConfigurationEvent

    class Confirm(val mode: ConfigurationMode) : FlightSheetConfigurationEvent

    data object Back : FlightSheetConfigurationEvent
}