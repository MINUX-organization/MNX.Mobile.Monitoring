package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.cryptos.api.CryptosProvider
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetGetDto
import com.minux.monitoring.feature.flightsheets.impl.data.repository.FlightSheetRepository
import com.minux.monitoring.feature.flightsheets.impl.data.repository.MinerRepository
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toDeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toFlightSheetChangeDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toFlightSheetInputDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toFlightSheetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toFlightSheetTargetInputs
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toMiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toMiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model.FlightSheetConfigurationAction
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model.FlightSheetConfigurationEvent
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model.FlightSheetConfigurationUiState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

internal class FlightSheetConfigurationViewModel @Inject constructor(
    private val flightSheetRepository: FlightSheetRepository,
    private val minerRepository: MinerRepository,
    private val cryptosProvider: CryptosProvider
) : BaseViewModel<FlightSheetConfigurationUiState,
            FlightSheetConfigurationAction,
            FlightSheetConfigurationEvent>(initialState = FlightSheetConfigurationUiState()) {

    override fun onEvent(uiEvent: FlightSheetConfigurationEvent) {
        when (uiEvent) {
            is FlightSheetConfigurationEvent.FetchFlightSheetParameters -> {
                fetchFlightSheetParameters(mode = uiEvent.mode)
            }

            is FlightSheetConfigurationEvent.NameChanged -> {
                flightSheetNameChanged(name = uiEvent.name)
            }

            is FlightSheetConfigurationEvent.TargetTypeChanged -> {
                targetTypeChanged(targetType = uiEvent.targetType)
            }

            is FlightSheetConfigurationEvent.CpuMinerChanged -> {
                cpuMinerChanged(cpuMiner = uiEvent.miner)
            }

            is FlightSheetConfigurationEvent.CpuHugePagesChanged -> {
                cpuHugePagesChanged(cpuHugePages = uiEvent.hugePages)
            }

            is FlightSheetConfigurationEvent.CpuThreadsCountChanged -> {
                cpuThreadsCountChanged(cpuThreadsCount = uiEvent.threadsCount)
            }

            is FlightSheetConfigurationEvent.CpuPoolChanged -> {
                cpuPoolChanged(
                    cpuPool = uiEvent.pool,
                    coinConfigIndex = uiEvent.coinConfigIndex
                )
            }

            is FlightSheetConfigurationEvent.CpuWalletChanged -> {
                cpuWalletChanged(
                    cpuWallet = uiEvent.wallet,
                    coinConfigIndex = uiEvent.coinConfigIndex
                )
            }

            is FlightSheetConfigurationEvent.CpuPoolPasswordChanged -> {
                cpuPoolPasswordChanged(
                    cpuPoolPassword = uiEvent.poolPassword,
                    coinConfigIndex = uiEvent.coinConfigIndex
                )
            }

            is FlightSheetConfigurationEvent.CpuMinerAdditionalArgumentsChanged -> {
                cpuMinerAdditionalArgumentsChanged(
                    cpuMinerAdditionalArguments = uiEvent.minerAdditionalArguments
                )
            }

            is FlightSheetConfigurationEvent.CpuMinerConfigFileChanged -> {
                cpuMinerConfigFileChanged(cpuMinerConfigFile = uiEvent.minerConfigFile)
            }

            is FlightSheetConfigurationEvent.GpuMinerChanged -> {
                gpuMinerChanged(gpuMiner = uiEvent.miner)
            }

            is FlightSheetConfigurationEvent.GpuMinerMiningModeChanged -> {
                gpuMinerMiningModeChanged(gpuMinerMiningMode = uiEvent.minerMiningMode)
            }

            is FlightSheetConfigurationEvent.GpuPoolChanged -> {
                gpuPoolChanged(
                    gpuPool = uiEvent.pool,
                    coinConfigIndex = uiEvent.coinConfigIndex
                )
            }

            is FlightSheetConfigurationEvent.GpuWalletChanged -> {
                gpuWalletChanged(
                    gpuWallet = uiEvent.wallet,
                    coinConfigIndex = uiEvent.coinConfigIndex
                )
            }

            is FlightSheetConfigurationEvent.GpuPoolPasswordChanged -> {
                gpuPoolPasswordChanged(
                    gpuPoolPassword = uiEvent.poolPassword,
                    coinConfigIndex = uiEvent.coinConfigIndex
                )
            }

            is FlightSheetConfigurationEvent.GpuMinerAdditionalArgumentsChanged -> {
                gpuMinerAdditionalArgumentsChanged(
                    gpuMinerAdditionalArguments = uiEvent.minerAdditionalArguments
                )
            }

            is FlightSheetConfigurationEvent.GpuMinerConfigFileChanged -> {
                gpuMinerConfigFileChanged(gpuMinerConfigFile = uiEvent.minerConfigFile)
            }

            is FlightSheetConfigurationEvent.Confirm -> configureFlightSheet(mode = uiEvent.mode)

            FlightSheetConfigurationEvent.Back -> {
                uiAction = FlightSheetConfigurationAction.OpenPreviousScreen
            }
        }
    }

    private fun fetchFlightSheetParameters(mode: ConfigurationMode) {
        uiState = uiState.copy(
            minersIsLoading = true,
            poolsIsLoading = true,
            walletsIsLoading = true
        )

        when (mode) {
            ConfigurationMode.Create -> createFlightSheet()

            is ConfigurationMode.Edit -> fetchFlightSheet(id = mode.flightSheetId)
        }
    }

    private fun createFlightSheet() {
        combine(
            minerRepository.getAvailableMiners(),
            cryptosProvider.getPools(),
            cryptosProvider.getWallets()
        ) { minersResult, poolsResult, walletsResult ->
            val miners = minersResult.getOrNull()?.map { it.toDeviceMinerModel() }
            val pools = poolsResult.getOrNull()?.map { it.toMiningPoolModel() }
            val wallets = walletsResult.getOrNull()?.map { it.toMiningWalletModel() }

            uiState = uiState.copy(
                minersIsLoading = false,
                miners = miners,
                poolsIsLoading = false,
                pools = pools,
                walletsIsLoading = false,
                wallets = wallets,
                flightSheet = FlightSheetInputModel(
                    targetInputs = uiState.flightSheet.selectedTargetType.toFlightSheetTargetInputs(
                        miners = miners,
                        pools = pools,
                        wallets = wallets
                    )
                )
            )
        }.launchIn(viewModelScope)
    }

    private fun fetchFlightSheet(id: String) {
        combine(
            minerRepository.getAvailableMiners(),
            cryptosProvider.getPools(),
            cryptosProvider.getWallets(),
            flightSheetRepository.getFlightSheet(flightSheetGet = FlightSheetGetDto(flightSheetId = id))
        ) { minersResult, poolsResult, walletsResult, flightSheetResult ->
            val miners = minersResult.getOrNull()?.map { it.toDeviceMinerModel() }
            val pools = poolsResult.getOrNull()?.map { it.toMiningPoolModel() }
            val wallets = walletsResult.getOrNull()?.map { it.toMiningWalletModel() }

            uiState = uiState.copy(
                minersIsLoading = false,
                miners = miners,
                poolsIsLoading = false,
                pools = pools,
                walletsIsLoading = false,
                wallets = wallets,
            )

            flightSheetResult.onSuccess {
                uiState = uiState.copy(
                    flightSheet = it.toFlightSheetInputModel(
                        miners = miners,
                        pools = pools,
                        wallets = wallets
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun flightSheetNameChanged(name: String) {
        val flightSheet = uiState.flightSheet

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(
                name = name,
                isNameValidationShowed = true,
                isNameValid = name.isNotEmpty()
            )
        )
    }

    private fun targetTypeChanged(targetType: FlightSheetTargetTypeModel) {
        val flightSheet = uiState.flightSheet

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(
                selectedTargetType = targetType,
                targetInputs = targetType.toFlightSheetTargetInputs(
                    miners = uiState.miners,
                    pools = uiState.pools,
                    wallets = uiState.wallets
                )
            )
        )
    }

    private fun cpuMinerChanged(cpuMiner: DeviceMinerModel?) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> {
                it.copy(selectedMiner = cpuMiner)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun cpuHugePagesChanged(cpuHugePages: String) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> { cpuTarget ->
                cpuTarget.copy(
                    hugePages = cpuHugePages,
                    isHugePagesValid = cpuHugePages.toIntOrNull()?.let { it > 0 } ?: false
                )
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun cpuThreadsCountChanged(cpuThreadsCount: String) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> { cpuTarget ->
                cpuTarget.copy(
                    threadsCount = cpuThreadsCount,
                    isThreadsCountValid = cpuThreadsCount.toIntOrNull()?.let { it > 0 } ?: false
                )
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun cpuPoolChanged(cpuPool: MiningPoolModel?, coinConfigIndex: Int) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> { cpuTarget ->
                val changedCoinConfigs = cpuTarget.coinConfigs.changeCoinConfig(index = coinConfigIndex) {
                    it.copy(selectedPool = cpuPool)
                }

                cpuTarget.copy(coinConfigs = changedCoinConfigs)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun cpuWalletChanged(cpuWallet: MiningWalletModel?, coinConfigIndex: Int) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> { cpuTarget ->
                val changedCoinConfigs = cpuTarget.coinConfigs.changeCoinConfig(index = coinConfigIndex) {
                    it.copy(selectedWallet = cpuWallet)
                }

                cpuTarget.copy(coinConfigs = changedCoinConfigs)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun cpuPoolPasswordChanged(cpuPoolPassword: String?, coinConfigIndex: Int) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> { cpuTarget ->
                val changedCoinConfigs = cpuTarget.coinConfigs.changeCoinConfig(index = coinConfigIndex) {
                    it.copy(poolPassword = cpuPoolPassword)
                }

                cpuTarget.copy(coinConfigs = changedCoinConfigs)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun cpuMinerAdditionalArgumentsChanged(cpuMinerAdditionalArguments: String) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> { cpuTarget ->
                cpuTarget.copy(minerAdditionalArguments = cpuMinerAdditionalArguments)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun cpuMinerConfigFileChanged(cpuMinerConfigFile: File?) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.CpuTargetInputModel> { cpuTarget ->
                cpuTarget.copy(minerConfigFile = cpuMinerConfigFile)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun gpuMinerChanged(gpuMiner: DeviceMinerModel?) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.GpuTargetInputModel> {
                it.copy(selectedMiner = gpuMiner)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun gpuMinerMiningModeChanged(gpuMinerMiningMode: MiningModeModel) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.GpuTargetInputModel> {
                it.copy(
                    selectedMiningMode = gpuMinerMiningMode,
                    coinConfigs = List(gpuMinerMiningMode.value) {
                        MiningCoinConfigInputModel(
                            selectedPool = uiState.pools?.firstOrNull(),
                            isPoolValid = !uiState.pools.isNullOrEmpty(),
                            selectedWallet = uiState.wallets?.firstOrNull(),
                            isWalletValid = !uiState.wallets.isNullOrEmpty()
                        )
                    }
                )
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun gpuPoolChanged(gpuPool: MiningPoolModel?, coinConfigIndex: Int) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.GpuTargetInputModel> { gpuTarget ->
                val changedCoinConfigs = gpuTarget.coinConfigs.changeCoinConfig(index = coinConfigIndex) {
                    it.copy(selectedPool = gpuPool)
                }

                gpuTarget.copy(coinConfigs = changedCoinConfigs)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun gpuWalletChanged(gpuWallet: MiningWalletModel?, coinConfigIndex: Int) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.GpuTargetInputModel> { gpuTarget ->
                val changedCoinConfigs = gpuTarget.coinConfigs.changeCoinConfig(index = coinConfigIndex) {
                    it.copy(selectedWallet = gpuWallet)
                }

                gpuTarget.copy(coinConfigs = changedCoinConfigs)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun gpuPoolPasswordChanged(gpuPoolPassword: String?, coinConfigIndex: Int) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.GpuTargetInputModel> { gpuTarget ->
                val changedCoinConfigs = gpuTarget.coinConfigs.changeCoinConfig(index = coinConfigIndex) {
                    it.copy(poolPassword = gpuPoolPassword)
                }

                gpuTarget.copy(coinConfigs = changedCoinConfigs)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun gpuMinerAdditionalArgumentsChanged(gpuMinerAdditionalArguments: String) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.GpuTargetInputModel> { gpuTarget ->
                gpuTarget.copy(minerAdditionalArguments = gpuMinerAdditionalArguments)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private fun gpuMinerConfigFileChanged(gpuMinerConfigFile: File?) {
        val flightSheet = uiState.flightSheet

        val changedTargetInputs = flightSheet.targetInputs
            .changeTarget<FlightSheetTargetInputModel.GpuTargetInputModel> { gpuTarget ->
                gpuTarget.copy(minerConfigFile = gpuMinerConfigFile)
            }

        uiState = uiState.copy(
            flightSheet = flightSheet.copy(targetInputs = changedTargetInputs)
        )
    }

    private inline fun <reified T : FlightSheetTargetInputModel> List<FlightSheetTargetInputModel>.changeTarget(
        noinline block: (T) -> T,
    ): List<FlightSheetTargetInputModel> {
        return toMutableList().apply {
            val targetIndex = this.indexOfFirst { it is T }
            this[targetIndex] = block(this[targetIndex] as T)
        }
    }

    private fun List<MiningCoinConfigInputModel>.changeCoinConfig(
        index: Int,
        block: (MiningCoinConfigInputModel) -> MiningCoinConfigInputModel
    ): List<MiningCoinConfigInputModel> {
        return toMutableList().apply {
            this[index] = block(this[index])
        }
    }

    private fun configureFlightSheet(mode: ConfigurationMode) {
        when (mode) {
            ConfigurationMode.Create -> confirmCreateFlightSheet()
            is ConfigurationMode.Edit -> confirmEditFlightSheet()
        }
    }

    private fun confirmCreateFlightSheet() {
        flightSheetRepository.addFlightSheet(
            flightSheetInput = uiState.flightSheet.toFlightSheetInputDto()
        ).onEach { result ->
            result.onSuccess {
                uiAction = FlightSheetConfigurationAction.OpenPreviousScreen
            }.onFailure {
                uiAction = FlightSheetConfigurationAction.ShowCreateFlightSheetFailedSnackBar
            }
        }.launchIn(viewModelScope)
    }

    private fun confirmEditFlightSheet() {
        flightSheetRepository.changeFlightSheet(
            flightSheetChange = uiState.flightSheet.toFlightSheetChangeDto()
        ).onEach { result ->
            result.onSuccess {
                uiAction = FlightSheetConfigurationAction.OpenPreviousScreen
            }.onFailure {
                uiAction = FlightSheetConfigurationAction.ShowChangeFlightSheetFailedSnackBar
            }
        }.launchIn(viewModelScope)
    }
}