package com.example.proyecto.ui.components.custom


import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction.Start
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.ui.theme.CustomTheme

@Composable
fun CustomTextButton(
    text: String, // Texto del botón
    textColor: Color = CustomTheme.deleteText, // Color del texto (valor por defecto: negro)
    backgroundColor: Color = Color.Transparent, // Color del fondo
    backgroundColorPress: Color = Color.Transparent, // Color del fondo
    onClick: () -> Unit, // Acción al hacer clic
    textAlign: TextAlign = TextAlign.Center
) {
    val interactionSource = remember { MutableInteractionSource() }

    TextButton(
        onClick = onClick,
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .then( // Cambiar fondo basado en estado presionado
                Modifier.background(
                    if (interactionSource.collectIsPressedAsState().value) backgroundColorPress
                    else backgroundColor
                )
            ),
        contentPadding = PaddingValues(0.dp), // Eliminar padding interno
        colors = ButtonDefaults.textButtonColors(
            contentColor = textColor
        ),
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
    }
}