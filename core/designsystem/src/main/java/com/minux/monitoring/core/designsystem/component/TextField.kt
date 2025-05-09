package com.minux.monitoring.core.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXTextFieldDefaults.cursorColor
import com.minux.monitoring.core.designsystem.component.MNXTextFieldDefaults.textColor
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(4.dp),
    colors: TextFieldColors = MNXTextFieldDefaults.colors(),
    contentPadding: PaddingValues = PaddingValues(8.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }

    val textColor = textStyle.color
        .takeOrElse {
            val focused = interactionSource.collectIsFocusedAsState().value
            colors.textColor(enabled, isError, focused)
        }

    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    Column(modifier = modifier.animateContentSize()) {
        label?.let {
            ProvideTextStyle(
                value = textStyle.copy(color = MaterialTheme.colorScheme.onBackground),
                content = it
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = when {
                        !isError && enabled -> MaterialTheme.colorScheme.primary

                        !enabled -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

                        else -> MaterialTheme.colorScheme.error
                    },
                    shape = shape
                ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(colors.cursorColor(isError)),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    isError = isError,
                    placeholder = {
                        ProvideTextStyle(textStyle) {
                            placeholder?.invoke()
                        }
                    },
                    prefix = prefix,
                    suffix = suffix,
                    shape = shape,
                    colors = colors,
                    contentPadding = contentPadding
                )
            }
        )

        if (isError && supportingText != null) {
            ProvideTextStyle(
                value = textStyle.copy(
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                ),
                content = supportingText
            )
        }
    }
}

object MNXTextFieldDefaults {

    @Composable
    fun colors(
        focusedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        unfocusedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        errorTextColor: Color = MaterialTheme.colorScheme.onErrorContainer,
        focusedContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        unfocusedContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        disabledContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        errorContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
        cursorColor: Color = MaterialTheme.colorScheme.primary,
        errorCursorColor: Color = MaterialTheme.colorScheme.error,
        selectionColors: TextSelectionColors = LocalTextSelectionColors.current,
        focusedPlaceholderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        unfocusedPlaceholderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledPlaceholderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        errorPlaceholderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        focusedPrefixColor: Color = MaterialTheme.colorScheme.primary,
        unfocusedPrefixColor: Color = MaterialTheme.colorScheme.primary,
        disabledPrefixColor: Color = MaterialTheme.colorScheme.primary,
        errorPrefixColor: Color = MaterialTheme.colorScheme.error,
        focusedSuffixColor: Color = MaterialTheme.colorScheme.primary,
        unfocusedSuffixColor: Color = MaterialTheme.colorScheme.primary,
        disabledSuffixColor: Color = MaterialTheme.colorScheme.primary,
        errorSuffixColor: Color = MaterialTheme.colorScheme.error,
    ) = TextFieldColors(
            focusedTextColor = focusedTextColor,
            unfocusedTextColor = unfocusedTextColor,
            disabledTextColor = disabledTextColor.copy(alpha = 0.25f),
            errorTextColor = errorTextColor,
            focusedContainerColor = focusedContainerColor,
            unfocusedContainerColor = unfocusedContainerColor,
            disabledContainerColor = disabledContainerColor.copy(alpha = 0.5f),
            errorContainerColor = errorContainerColor,
            cursorColor = cursorColor,
            errorCursorColor = errorCursorColor,
            textSelectionColors = selectionColors,
            focusedIndicatorColor = Color.Unspecified,
            unfocusedIndicatorColor = Color.Unspecified,
            disabledIndicatorColor = Color.Unspecified,
            errorIndicatorColor = Color.Unspecified,
            focusedLeadingIconColor = Color.Unspecified,
            unfocusedLeadingIconColor = Color.Unspecified,
            disabledLeadingIconColor = Color.Unspecified,
            errorLeadingIconColor = Color.Unspecified,
            focusedTrailingIconColor = Color.Unspecified,
            unfocusedTrailingIconColor = Color.Unspecified,
            disabledTrailingIconColor = Color.Unspecified,
            errorTrailingIconColor = Color.Unspecified,
            focusedLabelColor = Color.Unspecified,
            unfocusedLabelColor = Color.Unspecified,
            disabledLabelColor = Color.Unspecified,
            errorLabelColor = Color.Unspecified,
            focusedPlaceholderColor = focusedPlaceholderColor,
            unfocusedPlaceholderColor = unfocusedPlaceholderColor,
            disabledPlaceholderColor = disabledPlaceholderColor.copy(alpha = 0.25f),
            errorPlaceholderColor = errorPlaceholderColor,
            focusedSupportingTextColor = Color.Unspecified,
            unfocusedSupportingTextColor = Color.Unspecified,
            disabledSupportingTextColor = Color.Unspecified,
            errorSupportingTextColor = Color.Unspecified,
            focusedPrefixColor = focusedPrefixColor,
            unfocusedPrefixColor = unfocusedPrefixColor,
            disabledPrefixColor = disabledPrefixColor.copy(alpha = 0.5f),
            errorPrefixColor = errorPrefixColor,
            focusedSuffixColor = focusedSuffixColor,
            unfocusedSuffixColor = unfocusedSuffixColor,
            disabledSuffixColor = disabledSuffixColor.copy(alpha = 0.5f),
            errorSuffixColor = errorSuffixColor,
        )

    @Stable
    internal fun TextFieldColors.textColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean,
    ): Color =
        when {
            !enabled -> disabledTextColor
            isError -> errorTextColor
            focused -> focusedTextColor
            else -> unfocusedTextColor
        }

    @Stable
    internal fun TextFieldColors.cursorColor(isError: Boolean): Color =
        if (isError) errorCursorColor else cursorColor
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
                enabled = true,
                placeholder = { Text(text = "Login" ) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            MNXTextField(
                value = text.value,
                onValueChange = { text.value = it },
                enabled = false,
                placeholder = { Text(text = "Login" ) }
            )

            MNXTextField(
                value = text.value,
                onValueChange = { text.value = it },
                enabled = false,
                label = {
                    Text(
                        text = "Sample",
                        modifier = Modifier.padding(
                            top = 10.dp,
                            bottom = 2.dp
                        )
                    )
                },
                placeholder = { Text(text = "Search" ) },
                prefix = {
                    Icon(
                        modifier = Modifier.padding(end = 2.dp),
                        painter = painterResource(id = MNXIcons.Search),
                        contentDescription = "Search"
                    )
                },
                supportingText = { Text(text = "Sample") },
                isError = true,
                shape = RectangleShape,
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                    vertical = 8.dp
                )
            )
        }
    }
}