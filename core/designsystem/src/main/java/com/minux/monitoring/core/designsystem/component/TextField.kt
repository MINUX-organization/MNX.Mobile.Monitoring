package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    hintText: String = ""
) {
    /*OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(6.dp),
        singleLine = true,
        placeholder = {
            Text(text = "Login")
        }
    )*/

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val shape = RoundedCornerShape(4.dp)
    val textStyle = TextStyle(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize = 16.sp
    )

    BasicTextField(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            ),
        value = value,
        onValueChange = onValueChanged,
        interactionSource = interactionSource,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                shape = shape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                contentPadding = PaddingValues(
                    start = 10.dp,
                    top = 7.dp,
                    end = 10.dp,
                    bottom = 7.dp
                ),
                placeholder = {
                    Text(
                        text = hintText,
                        style = textStyle
                    )
                }
            )

            /*Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = shape
                    )
                    .border(
                        width = 1.5.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = shape
                    )
                    .padding(
                        start = 8.dp,
                        top = 6.dp,
                        end = 8.dp,
                        bottom = 6.dp
                    )
            ) {
                if (value.isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = hintText,
                        fontSize = fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                innerTextField()
            }*/
        }
    )

}

@Preview(showBackground = true)
@Composable
fun MNXTextFieldPreview() {
    MNXTheme {
        var text by remember {
            mutableStateOf("")
        }

        MNXTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = text,
            onValueChanged = { text = it },
            hintText = "Login"
        )
    }
}