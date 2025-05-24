package com.minux.monitoring.feature.auth.impl.presentation.ui.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.minux.monitoring.feature.auth.impl.presentation.model.AuthInfoModel
import com.minux.monitoring.feature.auth.impl.presentation.navigation.AuthFlowRoute
import com.minux.monitoring.feature.auth.impl.presentation.ui.common.AuthTextButton
import com.minux.monitoring.feature.auth.impl.presentation.ui.common.AuthTextField
import com.minux.monitoring.feature.auth.impl.presentation.ui.common.trianglesPaint

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel,
    onNavigate: (AuthFlowRoute) -> Unit,
    onNavigateToMainScreen: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    LoginScreen(
        loginUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .trianglesPaint()
            .safeDrawingPadding()
            .padding(vertical = 16.dp)
    )

    when (action) {
        LoginAction.OpenMainScreen -> onNavigateToMainScreen()

        LoginAction.OpenRegisterScreen -> { onNavigate(AuthFlowRoute.Register) }

        LoginAction.ShowLoginFailedSnackBar -> onShowSnackBar("Failed to login to the account.")

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun LoginScreen(
    loginUiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val passwordMaskChar = remember { mutableStateOf('⚹') }

        @OptIn(ExperimentalFoundationApi::class)
        Image(
            painter = painterResource(id = MNXIcons.Logo),
            contentDescription = "Logo",
            modifier = Modifier.combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onLongClick = {
                    if (passwordMaskChar.value == '⚹')
                        passwordMaskChar.value = '₿'
                    else
                        passwordMaskChar.value = '⚹'
                },
                onClick = {}
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        LoginInputFields(
            model = loginUiState.authInfo,
            passwordMaskChar = passwordMaskChar.value,
            onEvent = onEvent,
            modifier = Modifier.padding(horizontal = 56.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        MNXButton(onClick = { onEvent(LoginEvent.Login) }) {
            Text(
                text = "Login",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoginAdditions(
            onRegisterAccountClick = { onEvent(LoginEvent.RegisterAccount) }
        )
    }
}

@Composable
private fun LoginInputFields(
    model: AuthInfoModel,
    passwordMaskChar: Char,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(LoginEvent.LoginChanged(login = ""))
        onEvent(LoginEvent.PasswordChanged(password = ""))
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AuthTextField(
            value = model.login,
            onValueChange = { onEvent(LoginEvent.LoginChanged(login = it)) },
            label = "Login",
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Your login") },
            supportingText = {
                Text(
                    text = "Enter login",
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            },
            isError = model.isValidationShowed && !model.isLoginValid
        )

        AuthTextField(
            value = model.password,
            onValueChange = { onEvent(LoginEvent.PasswordChanged(password = it)) },
            label = "Password",
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Your password") },
            supportingText = {
                Text(
                    text = "Enter password",
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            },
            isError = model.isValidationShowed && !model.isPasswordValid,
            visualTransformation = PasswordVisualTransformation(mask = passwordMaskChar)
        )
    }
}

@Composable
private fun LoginAdditions(
    onRegisterAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don’t have an account?",
                color = MaterialTheme.colorScheme.onBackground
            )

            AuthTextButton(
                text = "Register now!",
                onClick = onRegisterAccountClick
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    MNXTheme {
        LoginScreen(
            loginUiState = LoginUiState(),
            onEvent = {},
            modifier = Modifier
                .fillMaxSize()
                .trianglesPaint()
        )
    }
}