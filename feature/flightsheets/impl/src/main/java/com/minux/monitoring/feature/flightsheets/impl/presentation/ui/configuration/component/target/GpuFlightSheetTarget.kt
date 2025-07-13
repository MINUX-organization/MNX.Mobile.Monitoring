package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.DeviceMinersDropDownMenu
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.MinerConfigFileTextField
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.MiningCoinConfigInputFields
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.MiningModeButton
import java.io.File

@Composable
internal fun GpuFlightSheetTarget(
    model: FlightSheetTargetInputModel.GpuTargetInputModel,
    minersIsLoading: Boolean,
    miners: List<DeviceMinerModel>?,
    miningModes: List<MiningModeModel>,
    poolsIsLoading: Boolean,
    pools: List<MiningPoolModel>?,
    walletsIsLoading: Boolean,
    wallets: List<MiningWalletModel>?,
    onMinerChange: (DeviceMinerModel?) -> Unit,
    onMiningModeChange: (MiningModeModel) -> Unit,
    onPoolChange: (MiningPoolModel?, Int) -> Unit,
    onWalletChange: (MiningWalletModel?, Int) -> Unit,
    onPoolPasswordChange: (String, Int) -> Unit,
    onMinerAdditionalArgumentsChange: (String) -> Unit,
    onMinerConfigFileChange: (File?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DeviceMinersDropDownMenu(
            minersIsLoading = minersIsLoading,
            miners = miners,
            selectedMiner = model.selectedMiner,
            onSelectedMinerChange = onMinerChange,
            label = {
                Text(
                    text = "GPU Miner",
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = LocalTextStyle.current
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Miner mining mode",
            color = MaterialTheme.colorScheme.onPrimary
        )

        MiningModeButton(
            miningModeLabels = listOf("Single", "Dual", "Triple"),
            miningModes = miningModes,
            supportedModes = model.selectedMiner?.supportedMiningModes ?: emptyList(),
            selectedMode = model.selectedMiningMode,
            onMiningModeChange = onMiningModeChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        model.coinConfigs.forEachIndexed { index, config ->
            MiningCoinConfigInputFields(
                model = config,
                poolsIsLoading = poolsIsLoading,
                pools = pools,
                poolsLabel = "GPU Pool ${index + 1}",
                walletsIsLoading = walletsIsLoading,
                wallets = wallets,
                walletsLabel = "GPU Wallet ${index + 1}",
                poolPasswordLabel = "GPU Pool Password ${index + 1}",
                onPoolChange = { onPoolChange(it, index) },
                onWalletChange = { onWalletChange(it, index) },
                onPoolPasswordChange = { onPoolPasswordChange(it, index) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        MNXTextField(
            value = model.minerAdditionalArguments ?: "",
            onValueChange = onMinerAdditionalArgumentsChange,
            label = {
                Text(
                    text = "GPU Miner Additional Arguments",
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            },
            placeholder = { Text(text = "Write miner additional arguments") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        MinerConfigFileTextField(
            value = model.minerConfigFile,
            onValueChange = onMinerConfigFileChange,
            label = "GPU Miner Config File"
        )
    }
}