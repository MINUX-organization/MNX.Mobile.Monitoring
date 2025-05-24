package com.minux.monitoring.feature.profile.impl.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.profile.impl.presentation.model.ProfileModel

@Composable
internal fun ProfileInfo(
    model: ProfileModel,
    onChangeNicknameClick: () -> Unit,
    onGenerateRigKeyClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ProfileParameter(
            name = "Login",
            value = model.login ?: "N/A"
        )

        ProfileParameterDivider()

        ProfileParameter(
            name = "Nickname",
            value = model.nickname ?: "N/A",
            onClick = onChangeNicknameClick
        )

        ProfileParameterDivider()

        ProfileParameter(
            name = "Registration date",
            value = model.registrationDate
        )

        ProfileParameterDivider()

        ProfileParameter(
            name = "Rig key",
            value = model.key ?: "Not generated",
            onClick = onGenerateRigKeyClick,
        )

        ProfileParameterDivider()

        ProfileParameter(
            name = "Password",
            value = "⚹".repeat(10),
            onClick = onChangePasswordClick
        )
    }
}

@Composable
internal fun ProfileInfoShimmer(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        repeat(5) {
            ProfileParameterShimmer()

            ProfileParameterDivider()
        }
    }
}

@Composable
internal fun ProfileInfoError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.MinuxError),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Failed to load profile info",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleSmall
        )
    }
}