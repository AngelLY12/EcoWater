package com.example.proyecto.ui.components.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.proyecto.ui.theme.mainColor

@Composable
fun NotificationScreen(navController: NavHostController) {
    Surface(
        color = mainColor, // Fondo azul oscuro similar al de la imagen
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            // Encabezado con botón de retroceso
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                androidx.compose.material.IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Retroceder",
                        tint = Color.White
                    )
                }


                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Notificaciones",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            NotificationItem(
                title = "Nivel alto",
                date = "09/03",
                message = "Notificar cuando la capacidad este a su capacidad máxima",
                icon = Icons.Default.Warning,
                iconColor = Color.Yellow
            )

            NotificationDivider()

            NotificationItem(
                title = "Tanque lleno",
                date = "09/03",
                message = "El tanque de agua esta a su máxima capacidad.",
                icon = Icons.Default.CheckCircle,
                iconColor = Color.Green
            )

            NotificationDivider()

            NotificationItem(
                title = "Nuevo dispositivo conectado",
                date = "08/03",
                message = "El dispositivo ESP32-XXX se ha conectado correctamente",
                icon = Icons.Default.Check,
                iconColor = Color.Blue
            )
        }
    }
}

@Composable
fun NotificationItem(
    title: String,
    date: String,
    message: String,
    icon: ImageVector,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.LightGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.LightGray
                )
            )
        }
    }
}

@Composable
fun NotificationDivider() {
    Divider(
        color = Color(0xFF1E4D7B), // Color del divisor más claro que el fondo
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun NotificationScreenPreview(navController: NavHostController) {
    NotificationScreen(navController = navController)
}
