package com.example.proyecto.ui.screens.users

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.proyecto.ui.theme.CustomTheme


@Composable
fun OutlinedTextFieldLR(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false
){
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = label ,
        singleLine = true,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CustomTheme.cardBorder,
            unfocusedBorderColor = CustomTheme.cardBorder,
            focusedLabelColor = CustomTheme.textOnPrimary,
            cursorColor = CustomTheme.textOnPrimary,
            focusedTextColor = CustomTheme.textOnPrimary,
            unfocusedTextColor = CustomTheme.cardBorder,
            errorTextColor = CustomTheme.error,
            errorLabelColor = CustomTheme.error,
            errorBorderColor = CustomTheme.error,
            errorTrailingIconColor = CustomTheme.error,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        visualTransformation = visualTransformation,
        trailingIcon =
            trailingIcon,
        keyboardOptions = keyboardOptions,
        isError = isError

    )

}