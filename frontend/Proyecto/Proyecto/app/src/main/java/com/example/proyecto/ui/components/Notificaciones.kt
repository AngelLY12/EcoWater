package com.example.proyecto.ui.components

import androidx.compose.foundation.Image // Importa Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // Importa painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.navigation.NavHostController
import com.example.proyecto.R // Importa R para acceder a los recursos

@Composable
fun DispositivosScreen(navController: NavHostController,onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón de retroceso y título
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_custom), // Usa la imagen personalizada
                    contentDescription = "Retroceder",
                    modifier = Modifier.size(24.dp) // Ajusta el tamaño de la imagen si es necesario
                )
            }
            Text(
                text = "Notificaciones",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notificación de nivel alto
        NotificationItem(
            title = "Nivel alto",
            date = "09/03",
            message = "Notificar cuando la capacidad este a su capacidad máxima"
        )
        Divider(color = Color.White, thickness = 2.dp)

        // Notificación de tanque lleno
        NotificationItem(
            title = "Tanque lleno",
            date = "09/03",
            message = "El tanque de agua esta a su máxima capacidad."
        )
        Divider(color = Color.White, thickness = 2.dp)

        // Notificación de nuevo dispositivo conectado
        NotificationItem(
            title = "Nuevo dispositivo conectado",
            date = "08/03",
            message = "El dispositivo ESP32-XXX se ha conectado correctamente"
        )
    }
}

@Composable
fun NotificationItem(title: String, date: String, message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp).padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(text = date, fontSize = 18.sp, color = Color.Gray)
        }
        Text(text = message, fontSize = 18.sp, color = Color.Gray)
    }
}

@Composable
fun DispositivosScreenPreview(navController: NavHostController) {
    Surface(color = Color(0xFF083257)) {
        DispositivosScreen(navController=navController,onBackClick = {})
    }
}