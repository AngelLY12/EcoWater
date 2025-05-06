package com.example.proyecto.ui.components.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.proyecto.ui.theme.mainColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> CustomDropdown(
    options: List<T>,
    label: String,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    optionToText: (T) -> String = { it.toString() },
    whiteBg: Boolean?=null
) {
    var expanded by remember { mutableStateOf(false) }
    val isWhiteBg = if (whiteBg == true) mainColor else Color.White

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption?.let { optionToText(it) } ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = isWhiteBg) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                textColor = isWhiteBg,
                placeholderColor = isWhiteBg,
                focusedLabelColor = isWhiteBg,
                unfocusedLabelColor = isWhiteBg,
                focusedBorderColor = isWhiteBg,
                unfocusedBorderColor = isWhiteBg,
                cursorColor = isWhiteBg,
                leadingIconColor = isWhiteBg
            ),
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(optionToText(option)) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
