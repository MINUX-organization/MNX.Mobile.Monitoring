package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.DeviceMinersDropDownMenu
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.MinerConfigFileTextField
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.MiningCoinConfigInputFields
import java.io.File

@Composable
internal fun CpuFlightSheetTarget(
    model: FlightSheetTargetInputModel.CpuTargetInputModel,
    minersIsLoading: Boolean,
    miners: List<DeviceMinerModel>?,
    poolsIsLoading: Boolean,
    pools: List<MiningPoolModel>?,
    walletsIsLoading: Boolean,
    wallets: List<MiningWalletModel>?,
    onMinerChange: (DeviceMinerModel?) -> Unit,
    onHugePagesChange: (String) -> Unit,
    onThreadsCountChange: (String) -> Unit,
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
                    text = "CPU Miner",
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = LocalTextStyle.current
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        MNXTextField(
            value = model.hugePages ?: "",
            onValueChange = onHugePagesChange,
            label = {
                Text(
                    text = "CPU Huge Pages",
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            },
            placeholder = { Text(text = "Enter huge pages") },
            supportingText = {
                Text(
                    text = "Must be a number",
                    modifier = Modifier.padding(start = 2.dp)
                )
            },
            isError = !model.isHugePagesValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        MNXTextField(
            value = model.threadsCount ?: "",
            onValueChange = onThreadsCountChange,
            label = {
                Text(
                    text = "CPU Threads Count",
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            },
            placeholder = { Text(text = "Enter threads count") },
            supportingText = {
                Text(
                    text = "Must be a number",
                    modifier = Modifier.padding(start = 2.dp)
                )
            },
            isError = !model.isThreadsCountValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        model.coinConfigs.forEachIndexed { index, config ->
            MiningCoinConfigInputFields(
                model = config,
                poolsIsLoading = poolsIsLoading,
                pools = pools,
                poolsLabel = "CPU Pool ${index + 1}",
                walletsIsLoading = walletsIsLoading,
                wallets = wallets,
                walletsLabel = "CPU Wallet ${index + 1}",
                poolPasswordLabel = "CPU Pool Password ${index + 1}",
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
                    text = "CPU Miner Additional Arguments",
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            },
            placeholder = { Text(text = "Write miner additional arguments") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        MinerConfigFileTextField(
            value = model.minerConfigFile,
            onValueChange = onMinerConfigFileChange,
            label = "CPU Miner Config File"
        )
    }
}