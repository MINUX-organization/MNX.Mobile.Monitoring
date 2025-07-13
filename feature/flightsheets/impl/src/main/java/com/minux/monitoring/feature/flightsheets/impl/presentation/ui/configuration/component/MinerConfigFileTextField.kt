package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import com.minux.monitoring.core.designsystem.component.MNXTextField
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@Composable
internal fun MinerConfigFileTextField(
    value: File?,
    onValueChange: (File?) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    val source = remember { MutableInteractionSource() }

    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { fileUri ->
        if (fileUri == null) return@rememberLauncherForActivityResult

        onValueChange(fileUri.toFile())
    }

    LaunchedEffect(source) {
        source.interactions.collectLatest {
            if (it is PressInteraction.Release) {
                fileLauncher.launch("text/plain")
            }
        }
    }

    MNXTextField(
        value = value?.name ?: "",
        onValueChange = {},
        modifier = modifier,
        readOnly = true,
        label = {
            Text(
                text = label,
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        },
        placeholder = { Text(text = "Upload miner config file") },
        prefix = {
            // TODO: Add upload icon
        },
        interactionSource = source
    )
}