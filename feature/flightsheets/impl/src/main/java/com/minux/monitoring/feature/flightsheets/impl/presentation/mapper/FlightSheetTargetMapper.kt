package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetTargetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetTargetInputDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceMiningConfigDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceMiningConfigInputDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel

internal fun FlightSheetTargetDto.toFlightSheetTargetModel(): FlightSheetTargetModel? {
    return when (miningConfig) {
        is DeviceMiningConfigDto.CpuMiningConfigDto -> {
            FlightSheetTargetModel.CpuTargetModel(
                miner = miner?.toDeviceMinerModel(),
                coinConfigs = miningConfig.coinConfigs.mapNotNull { it?.toMiningCoinConfigModel() },
                additionalArguments = miningConfig.additionalArguments,
                configFileContent = miningConfig.configFileContent,
                hugePages = miningConfig.hugePages?.toString(),
                threadsCount = miningConfig.threadsCount?.toString()
            )
        }

        is DeviceMiningConfigDto.GpuMiningConfigDto -> {
            FlightSheetTargetModel.GpuTargetModel(
                miner = miner?.toDeviceMinerModel(),
                coinConfigs = miningConfig.coinConfigs.mapNotNull { it?.toMiningCoinConfigModel() },
                additionalArguments = miningConfig.additionalArguments,
                configFileContent = miningConfig.configFileContent
            )
        }

        null -> null
    }
}

internal fun FlightSheetTargetDto.toFlightSheetTargetInputModel(
    miners: List<DeviceMinerModel>?,
    pools: List<MiningPoolModel>?,
    wallets: List<MiningWalletModel>?
): FlightSheetTargetInputModel? {
    val selectedMiner = miner?.toDeviceMinerModel() ?: miners?.firstOrNull()

    return when (miningConfig) {
        is DeviceMiningConfigDto.CpuMiningConfigDto -> {
            FlightSheetTargetInputModel.CpuTargetInputModel(
                selectedMiner = selectedMiner,
                isMinerValid = selectedMiner != null,
                hugePages = miningConfig.hugePages?.toString(),
                threadsCount = miningConfig.threadsCount?.toString(),
                coinConfigs = miningConfig.coinConfigs.mapNotNull {
                    it?.toMiningCoinConfigInputModel(
                        pools = pools,
                        wallets = wallets
                    )
                },
                minerAdditionalArguments = miningConfig.additionalArguments,
                minerConfigFile = null
            )
        }

        is DeviceMiningConfigDto.GpuMiningConfigDto -> {
            val coinConfigs = miningConfig.coinConfigs.mapNotNull {
                it?.toMiningCoinConfigInputModel(
                    pools = pools,
                    wallets = wallets
                )
            }

            FlightSheetTargetInputModel.GpuTargetInputModel(
                selectedMiner = selectedMiner,
                isMinerValid = selectedMiner != null,
                selectedMiningMode = MiningModeModel.entries[coinConfigs.size - 1],
                coinConfigs = coinConfigs,
                minerAdditionalArguments = miningConfig.additionalArguments,
                minerConfigFile = null
            )
        }

        null -> null
    }
}

internal fun FlightSheetTargetTypeModel.toFlightSheetTargetInputs(
    miners: List<DeviceMinerModel>?,
    pools: List<MiningPoolModel>?,
    wallets: List<MiningWalletModel>?
): List<FlightSheetTargetInputModel> {
    val selectedMiner = miners?.firstOrNull()
    val selectedPool = pools?.firstOrNull()
    val selectedWallet = wallets?.firstOrNull()

    val coinConfig = MiningCoinConfigInputModel(
        selectedPool = selectedPool,
        isPoolValid = selectedPool != null,
        selectedWallet = selectedWallet,
        isWalletValid = selectedWallet != null
    )

    val cpuTarget = FlightSheetTargetInputModel.CpuTargetInputModel(
        selectedMiner = selectedMiner,
        isMinerValid = selectedMiner != null,
        coinConfigs = listOf(coinConfig)
    )

    val gpuTarget = FlightSheetTargetInputModel.GpuTargetInputModel(
        selectedMiner = selectedMiner,
        isMinerValid = selectedMiner != null,
        coinConfigs = listOf(coinConfig)
    )

    return when (this) {
        FlightSheetTargetTypeModel.Rig -> listOf(cpuTarget, gpuTarget)

        FlightSheetTargetTypeModel.CPU -> listOf(cpuTarget)

        FlightSheetTargetTypeModel.GPU -> listOf(gpuTarget)
    }
}

internal fun List<FlightSheetTargetInputModel>.toFlightSheetTargetTypeModel(): FlightSheetTargetTypeModel {
    return when {
        all { it is FlightSheetTargetInputModel.CpuTargetInputModel } -> FlightSheetTargetTypeModel.CPU

        all { it is FlightSheetTargetInputModel.GpuTargetInputModel } -> FlightSheetTargetTypeModel.GPU

        else -> FlightSheetTargetTypeModel.Rig
    }
}

internal fun FlightSheetTargetInputModel.toFlightSheetTargetInputDto(): FlightSheetTargetInputDto {
    return FlightSheetTargetInputDto(
        miningConfig = when (this) {
            is FlightSheetTargetInputModel.CpuTargetInputModel -> {
                DeviceMiningConfigInputDto.CpuMiningConfigInputDto(
                    coinConfigs = coinConfigs.map { it.toMiningCoinConfigInputDto() },
                    additionalArguments = minerAdditionalArguments,
                    configFileContent = minerConfigFile?.readBytes()?.decodeToString(),
                    hugePages = hugePages?.toIntOrNull(),
                    threadsCount = threadsCount?.toIntOrNull()
                )
            }

            is FlightSheetTargetInputModel.GpuTargetInputModel -> {
                DeviceMiningConfigInputDto.GpuMiningConfigInputDto(
                    coinConfigs = coinConfigs.map { it.toMiningCoinConfigInputDto() },
                    additionalArguments = minerAdditionalArguments,
                    configFileContent = minerConfigFile?.readBytes()?.decodeToString()
                )
            }
        },
        minerId = selectedMiner!!.id
    )
}