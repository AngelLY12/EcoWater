package com.example.proyecto.ui.screens.tanks


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import com.example.proyecto.ui.theme.CustomTheme

@Composable
fun DateSelector(onDateSelected: (Date) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDate = calendar.time // Obtener la fecha seleccionada
    val dateFormatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))

    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth, 0, 0, 0)
                selectedDate = calendar.time // Obtener la fecha seleccionada
                onDateSelected(selectedDate)  // Llamar al callback para pasar la fecha seleccionada
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Colocamos el botón de selección de fecha con un tamaño adecuado
    Button(
        onClick = { datePickerDialog.show() },
        modifier = Modifier
            .fillMaxWidth(), // Ajustar el ancho del botón para que no ocupe toda la pantalla
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // Hace el fondo transparente
            contentColor = CustomTheme.textOnPrimary // Define el color del texto
        )

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                dateFormatter.format(selectedDate),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp)) // Ajusta el espacio entre el texto e icono
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Seleccionar fecha",
                tint = CustomTheme.textPrimary
            )
        }
    }
}