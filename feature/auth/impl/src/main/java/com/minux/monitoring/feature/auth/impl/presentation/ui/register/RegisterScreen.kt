package com.minux.monitoring.feature.auth.impl.presentation.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.auth.impl.presentation.mapper.toMessage
import com.minux.monitoring.feature.auth.impl.presentation.model.RegisterInfoModel
import com.minux.monitoring.feature.auth.impl.presentation.navigation.AuthFlowRoute
import com.minux.monitoring.feature.auth.impl.presentation.ui.common.AuthTextButton
import com.minux.monitoring.feature.auth.impl.presentation.ui.common.AuthTextField
import com.minux.monitoring.feature.auth.impl.presentation.ui.common.trianglesPaint

@Composable
internal fun RegisterRoute(
    viewModel: RegisterViewModel,
    onNavigate: (AuthFlowRoute) -> Unit,
    onNavigateToMainScreen: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    RegisterScreen(
        registerUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .trianglesPaint()
            .padding(vertical = 16.dp)
    )

    when (action) {
        RegisterAction.OpenMainScreen -> onNavigateToMainScreen()

        RegisterAction.OpenLoginScreen -> onNavigate(AuthFlowRoute.Login)

        RegisterAction.ShowRegisterFailedSnackBar -> onShowSnackBar("Failed to register account.")

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun RegisterScreen(
    registerUiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = MNXIcons.Logo),
            contentDescription = "Logo",
        )

        Spacer(modifier = Modifier.height(32.dp))

        RegisterInputFields(
            model = registerUiState.registerInfo,
            onEvent = onEvent,
            modifier = Modifier.padding(horizontal = 56.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        MNXButton(
            onClick = { onEvent(RegisterEvent.Register) }
        ) {
            Text(
                text = "Register",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        RegisterAdditions(onLoginAccountClick = { onEvent(RegisterEvent.LoginAccount) })
    }
}

@Composable
private fun RegisterInputFields(
    model: RegisterInfoModel,
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(RegisterEvent.LoginChanged(login = ""))
        onEvent(RegisterEvent.PasswordChanged(password = ""))
        onEvent(RegisterEvent.PasswordConfirmChanged(passwordConfirm = ""))
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AuthTextField(
            value = model.login,
            onValueChange = { onEvent(RegisterEvent.LoginChanged(login = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = "Login",
            placeholder = { Text(text = "Your login") },
            supportingText = {
                Text(
                    text = "Enter login",
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            },
            isError = model.isValidationShowed && !model.isLoginValid
        )

        val passwordValidationErrors = remember(model.passwordValidationResult.errors) {
            model.passwordValidationResult.errors
                .joinToString(separator = "\n") { it.toMessage() }
        }

        AuthTextField(
            value = model.password,
            onValueChange = { onEvent(RegisterEvent.PasswordChanged(password = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = "Password",
            placeholder = { Text(text = "Your password") },
            supportingText = {
                val supportingModifier = if (model.passwordValidationResult.errors.count() > 1) {
                    Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(top = 4.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                } else {
                    Modifier.padding(horizontal = 2.dp)
                }

                Box(modifier = supportingModifier) {
                    Text(text = passwordValidationErrors)
                }
            },
            isError = model.isValidationShowed && !model.passwordValidationResult.isValid,
            visualTransformation = PasswordVisualTransformation(mask = '⚹')
        )

        AuthTextField(
            value = model.passwordConfirm,
            onValueChange = { onEvent(RegisterEvent.PasswordConfirmChanged(passwordConfirm = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = "Confirm password",
            placeholder = { Text(text = "Your password again") },
            supportingText = {
                Text(
                    text = "Passwords must match",
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            },
            isError = model.isValidationShowed && !model.isPasswordConfirmValid,
            visualTransformation = PasswordVisualTransformation(mask = '⚹')
        )
    }
}

@Composable
private fun RegisterAdditions(
    onLoginAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Already have an account?",
            color = MaterialTheme.colorScheme.onBackground
        )

        AuthTextButton(
            text = "Login!",
            onClick = onLoginAccountClick
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterScreen(
                registerUiState = RegisterUiState(),
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .trianglesPaint()
            )
        }
    }
}