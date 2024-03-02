package com.minux.monitoring.feature.sign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXRoundedButton
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
internal fun SignInScreen(
    onLoginClick: (login: String, password: String) -> Unit = { _, _ -> },
    onForgotPasswordClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val loginText = remember {
            mutableStateOf("")
        }

        val passwordText = remember {
            mutableStateOf("")
        }

        Image(
            painter = painterResource(id = MNXIcons.Logo),
            contentDescription = "Логотип приложения"
        )

        SignInTextField(
            modifier = Modifier.padding(
                start = 64.dp,
                top = 40.dp,
                end = 64.dp
            ),
            contentModifier = Modifier.fillMaxWidth(),
            text = "Login",
            value = loginText.value,
            onValueChange = { loginText.value = it }
        )

        SignInTextField(
            modifier = Modifier.padding(
                start = 64.dp,
                top = 8.dp,
                end = 64.dp
            ),
            contentModifier = Modifier.fillMaxWidth(),
            text = "Password",
            value = passwordText.value,
            onValueChange = { passwordText.value = it },
            visualTransformation = PasswordVisualTransformation()
        )

        MNXRoundedButton(
            modifier = Modifier.padding(top = 20.dp),
            onClick = { onLoginClick(loginText.value, passwordText.value) },
            text = "Login"
        )
        
        Text(
            modifier = Modifier
                .padding(top = 64.dp)
                .clickable(onClick = onForgotPasswordClick),
            text = "Forgot password...",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontFamily = grillSansMtFamily,
            fontWeight = FontWeight.Normal,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
private fun SignInTextField(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontFamily = grillSansMtFamily,
            fontWeight = FontWeight.Normal
        )

        MNXTextField(
            modifier = contentModifier,
            value = value,
            onValueChange = onValueChange,
            hintText = text,
            visualTransformation = visualTransformation
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
    MNXTheme {
        SignInScreen()
    }
}