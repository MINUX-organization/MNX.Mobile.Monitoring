package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    hintText: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape = RoundedCornerShape(4.dp),
    prefix: @Composable () -> Unit = {},
    suffix: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(
        start = 7.dp,
        top = 7.dp,
        end = 7.dp,
        bottom = 7.dp
    )
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val textStyle = TextStyle(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal
    )

    BasicTextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            ),
        value = value,
        readOnly = readOnly,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                shape = shape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                contentPadding = contentPadding,
                placeholder = {
                    Text(
                        text = hintText,
                        style = textStyle
                    )
                },
                prefix = prefix,
                suffix = suffix
            )
        }
    )
}

@Preview
@Composable
private fun MNXTextFieldPreview() {
    MNXTheme {
        Column(modifier = Modifier.padding(10.dp)) {
            val text = remember {
                mutableStateOf("")
            }

            MNXTextField(
                value = text.value,
                onValueChange = { text.value = it },
                hintText = "Login"
            )

            MNXTextField(
                modifier = Modifier.padding(top = 10.dp),
                shape = RectangleShape,
                prefix = {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        painter = painterResource(id = MNXIcons.Search),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Search"
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = 7.dp,
                    vertical = 9.dp
                ),
                value = text.value,
                onValueChange = { text.value = it },
                hintText = "Login",
            )
        }
    }
}