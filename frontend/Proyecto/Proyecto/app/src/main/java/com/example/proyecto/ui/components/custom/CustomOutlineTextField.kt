package com.example.proyecto.ui.components.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.example.proyecto.ui.theme.mainColor
import kotlin.Boolean


@Composable
fun CustomOutlinedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    whiteBg: Boolean?=null

)
{
    val isWhiteBg = if (whiteBg == true) mainColor else Color.White

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = isWhiteBg) },
    isError: Boolean = false)
{
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            },
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = isWhiteBg,
            unfocusedBorderColor = isWhiteBg,
            focusedLabelColor = isWhiteBg,
            cursorColor = isWhiteBg,
            focusedTextColor = isWhiteBg,
            unfocusedTextColor = isWhiteBg
        ),
        keyboardOptions = keyboardOptions,
        isError = isError,
        singleLine = true
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        keyboardOptions = keyboardOptions,
        isError = isError
    )
}
