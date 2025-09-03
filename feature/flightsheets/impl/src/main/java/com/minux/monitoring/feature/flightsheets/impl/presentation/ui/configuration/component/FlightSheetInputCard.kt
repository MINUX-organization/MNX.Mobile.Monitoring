package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.target.CpuFlightSheetTarget
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.target.GpuFlightSheetTarget
import java.io.File

@Composable
internal fun FlightSheetInputCard(
    model: FlightSheetInputModel,
    targetTypes: List<FlightSheetTargetTypeModel>,
    minersIsLoading: Boolean,
    miners: List<DeviceMinerModel>?,
    miningModes: List<MiningModeModel>,
    poolsIsLoading: Boolean,
    pools: List<MiningPoolModel>?,
    walletsIsLoading: Boolean,
    wallets: List<MiningWalletModel>?,
    onNameChange: (String) -> Unit,
    onTargetTypeChange: (FlightSheetTargetTypeModel) -> Unit,
    onCpuMinerChange: (DeviceMinerModel?) -> Unit,
    onCpuHugePagesChange: (String) -> Unit,
    onCpuThreadsCountChange: (String) -> Unit,
    onCpuPoolChange: (MiningPoolModel?, Int) -> Unit,
    onCpuWalletChange: (MiningWalletModel?, Int) -> Unit,
    onCpuPoolPasswordChange: (String, Int) -> Unit,
    onCpuMinerAdditionalArgumentsChange: (String) -> Unit,
    onCpuMinerConfigFileChange: (File?) -> Unit,
    onGpuMinerChange: (DeviceMinerModel?) -> Unit,
    onGpuMinerMiningModeChange: (MiningModeModel) -> Unit,
    onGpuPoolChange: (MiningPoolModel?, Int) -> Unit,
    onGpuWalletChange: (MiningWalletModel?, Int) -> Unit,
    onGpuPoolPasswordChange: (String, Int) -> Unit,
    onGpuMinerAdditionalArgumentsChange: (String) -> Unit,
    onGpuMinerConfigFileChange: (File?) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXCard(
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            MNXTextField(
                value = model.name ?: "",
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Flight sheet name",
                        modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                    )
                },
                placeholder = { Text(text = "Enter name") },
                supportingText = {
                    Text(
                        text = "Name is empty",
                        modifier = Modifier.padding(start = 2.dp)
                    )
                },
                isError = model.isNameValidationShowed && !model.isNameValid
            )

            Spacer(modifier = Modifier.height(8.dp))

            MNXDropDownMenu(
                menuItems = targetTypes,
                selectedMenuItem = model.selectedTargetType,
                onSelectedMenuItemChange = onTargetTypeChange,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Target",
                        modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 1.dp, vertical = 4.dp),
                thickness = 0.5.dp
            )

            LazyColumn(modifier = Modifier.animateContentSize()) {
                itemsIndexed(model.targetInputs) { index, target ->
                    if (index != 0) {
                        Spacer(modifier = Modifier.height(8.dp))

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 1.dp, vertical = 4.dp),
                            thickness = 0.5.dp
                        )
                    }

                    when (target) {
                        is FlightSheetTargetInputModel.CpuTargetInputModel -> CpuFlightSheetTarget(
                            model = target,
                            minersIsLoading = minersIsLoading,
                            miners = miners,
                            poolsIsLoading = poolsIsLoading,
                            pools = pools,
                            walletsIsLoading = walletsIsLoading,
                            wallets = wallets,
                            onMinerChange = onCpuMinerChange,
                            onHugePagesChange = onCpuHugePagesChange,
                            onThreadsCountChange = onCpuThreadsCountChange,
                            onPoolChange = onCpuPoolChange,
                            onWalletChange = onCpuWalletChange,
                            onPoolPasswordChange = onCpuPoolPasswordChange,
                            onMinerAdditionalArgumentsChange = onCpuMinerAdditionalArgumentsChange,
                            onMinerConfigFileChange = onCpuMinerConfigFileChange
                        )

                        is FlightSheetTargetInputModel.GpuTargetInputModel -> GpuFlightSheetTarget(
                            model = target,
                            minersIsLoading = minersIsLoading,
                            miners = miners,
                            miningModes = miningModes,
                            poolsIsLoading = poolsIsLoading,
                            pools = pools,
                            walletsIsLoading = walletsIsLoading,
                            wallets = wallets,
                            onMinerChange = onGpuMinerChange,
                            onMiningModeChange = onGpuMinerMiningModeChange,
                            onPoolChange = onGpuPoolChange,
                            onWalletChange = onGpuWalletChange,
                            onPoolPasswordChange = onGpuPoolPasswordChange,
                            onMinerAdditionalArgumentsChange = onGpuMinerAdditionalArgumentsChange,
                            onMinerConfigFileChange = onGpuMinerConfigFileChange
                        )
                    }
                }
            }
        }
    }
}