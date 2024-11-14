package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.minux.monitoring.core.designsystem.component.MNXTextFieldDefaults.cursorColor
import com.minux.monitoring.core.designsystem.component.MNXTextFieldDefaults.textColor
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(4.dp),
    colors: TextFieldColors = MNXTextFieldDefaults.colors(),
    contentPadding: PaddingValues = PaddingValues(8.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }

    val enabled = true
    val isError = false

    val textColor = textStyle.color
        .takeOrElse {
            val focused = interactionSource.collectIsFocusedAsState().value
            colors.textColor(enabled, isError, focused)
        }

    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
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
}

object MNXTextFieldDefaults {

    @Composable
    fun colors(
        focusedTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        unfocusedTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledTextColor: Color = Color.Unspecified,
        errorTextColor: Color = Color.Unspecified,
        focusedContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        unfocusedContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        disabledContainerColor: Color = Color.Unspecified,
        errorContainerColor: Color = Color.Unspecified,
        cursorColor: Color = MaterialTheme.colorScheme.primary,
        errorCursorColor: Color = Color.Unspecified,
        selectionColors: TextSelectionColors = LocalTextSelectionColors.current,
        focusedPlaceholderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        unfocusedPlaceholderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledPlaceholderColor: Color = Color.Unspecified,
        errorPlaceholderColor: Color = Color.Unspecified,
        focusedPrefixColor: Color = Color.Unspecified,
        unfocusedPrefixColor: Color = Color.Unspecified,
        disabledPrefixColor: Color = Color.Unspecified,
        errorPrefixColor: Color = Color.Unspecified,
        focusedSuffixColor: Color = Color.Unspecified,
        unfocusedSuffixColor: Color = Color.Unspecified,
        disabledSuffixColor: Color = Color.Unspecified,
        errorSuffixColor: Color = Color.Unspecified,
    ) = TextFieldColors(
            focusedTextColor = focusedTextColor,
            unfocusedTextColor = unfocusedTextColor,
            disabledTextColor = disabledTextColor,
            errorTextColor = errorTextColor,
            focusedContainerColor = focusedContainerColor,
            unfocusedContainerColor = unfocusedContainerColor,
            disabledContainerColor = disabledContainerColor,
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
            disabledPlaceholderColor = disabledPlaceholderColor,
            errorPlaceholderColor = errorPlaceholderColor,
            focusedSupportingTextColor = Color.Unspecified,
            unfocusedSupportingTextColor = Color.Unspecified,
            disabledSupportingTextColor = Color.Unspecified,
            errorSupportingTextColor = Color.Unspecified,
            focusedPrefixColor = focusedPrefixColor,
            unfocusedPrefixColor = unfocusedPrefixColor,
            disabledPrefixColor = disabledPrefixColor,
            errorPrefixColor = errorPrefixColor,
            focusedSuffixColor = focusedSuffixColor,
            unfocusedSuffixColor = unfocusedSuffixColor,
            disabledSuffixColor = disabledSuffixColor,
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
                placeholder = { Text(text = "Login" ) }
            )

            MNXTextField(
                value = text.value,
                onValueChange = { text.value = it },
                modifier = Modifier.padding(top = 10.dp),
                textStyle = TextStyle(fontFamily = grillSansMtFamily),
                placeholder = { Text(text = "Search" ) },
                prefix = {
                    Icon(
                        modifier = Modifier.padding(end = 2.dp),
                        painter = painterResource(id = MNXIcons.Search),
                        contentDescription = "Search"
                    )
                },
                shape = RectangleShape,
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                    vertical = 8.dp
                )
            )
        }
    }
}