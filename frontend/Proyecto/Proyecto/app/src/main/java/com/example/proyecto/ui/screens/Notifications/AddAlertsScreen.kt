package com.example.proyecto.ui.screens.Notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun AddAlertsScreen(navController: NavHostController) {
    Scaffold(

        containerColor = Color(0xFF043B64)
    ) { innerPadding ->


        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                       Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retroceder",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "Alertas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }

            item {
                Text("Notificaciones predeterminadas", style = MaterialTheme.typography.subtitle2, color = Color.White)
                Spacer(Modifier.height(12.dp))
            }


            item {
                SectionWithCheckbox("Nivel de agua")
                AddAlertItem(title = "Nivel de agua", alertType = "Nivel alto", description = "80% de capacidad", unit = "%", threshold = "80")
                AddAlertItem(title = "Nivel de agua", alertType = "Nivel medio", description = "50% capacidad", unit = "%", threshold = "50")
                AddAlertItem(title = "Nivel de agua" , alertType = "Nivel bajo", description = "15% de capacidad", unit = "%", threshold = "15")
            }

            item {
                Spacer(Modifier.height(24.dp))
                SectionWithCheckbox("Consumo de agua")
                AddAlertItem(title = "Consumo de agua", alertType = "Consumo excesivo", description = "Más de 800 litros", unit = "L", threshold = "800")

            }

            item {
                Spacer(Modifier.height(24.dp))
                SectionWithCheckbox("Tanque")
                AddAlertItem(title = "Nivel de tanque", alertType = "Tanque lleno", description = "Tanque a su máxima capacidad", unit = "%", threshold = "100")
            }


        }
    }
}
