package com.example.proyecto.ui.screens.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.model.models.Device
import com.example.proyecto.model.models.DeviceRequest

@Composable
fun DeviceItem(device: Device) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Acción para ver más detalles del dispositivo */ },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFF083257))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Titulo y nombre del dispositivo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Nombre",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = device.deviceName ?: "No name",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                }
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ir",
                    tint = Color.White
                )
            }

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            // Titulo de propiedades
            Text(
                text = "PROPIEDADES",
                fontSize = 20.sp,
                color = Color.Gray
            )


            // Colocado en
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Colocado en ${if (device.deviceType.toString() == "SENSOR_PROXIMIDAD") "Tinaco" else "Tubería"}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = device.deviceLocation ?: "No location",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                }
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ir",
                    tint = Color.White
                )
            }

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            // Tipo de dispositivo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tipo de dispositivo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = device.deviceType.toString() ?: "No type",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                }
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ir",
                    tint = Color.White
                )
            }

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            // Eliminar y Divider
            Text(
                text = "Eliminar",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}
