package com.example.proyecto.ui.components.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.proyecto.ui.theme.CustomTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> CustomDropdown(
    options: List<T>,
    label: String,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    optionToText: (T) -> String = { it.toString() },
) {

    var expanded by remember { mutableStateOf(false) }
    val text = CustomTheme.textOnPrimary

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption?.let { optionToText(it) } ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = text) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                textColor = text,
                placeholderColor = text,
                focusedLabelColor = text,
                unfocusedLabelColor = text,
                focusedBorderColor = text,
                unfocusedBorderColor = text,
                cursorColor = text,
                leadingIconColor = text
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
