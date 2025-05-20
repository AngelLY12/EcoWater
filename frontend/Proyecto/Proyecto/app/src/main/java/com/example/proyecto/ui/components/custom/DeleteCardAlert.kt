package com.example.proyecto.ui.components.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.proyecto.ui.theme.CustomTheme


@Composable
fun DeleteCardAlert(
    title: String,
    text: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(title, color = CustomTheme.deleteText, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text, color = CustomTheme.textOnPrimary, fontWeight = FontWeight.Black)
            },
            dismissButton = {
                OutlinedButton(onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(1.dp, CustomTheme.cardBorder),
                ) {
                    Text("Cancelar", color =CustomTheme.outLinedButton)
                }

            },
            confirmButton = {
                Button(onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(backgroundColor = CustomTheme.normalButton),
                    ) {
                    Text("Eliminar", color = CustomTheme.textPrimary)
                }
            },

            modifier = Modifier
                .padding(8.dp),
            backgroundColor = CustomTheme.modalBackground
        )
    }
}
