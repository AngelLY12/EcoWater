package com.example.proyecto.ui.screens.Notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto.R
import com.example.proyecto.model.alert.UserAlertSettings
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.theme.Green
import com.example.proyecto.ui.viewModels.AlertsViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun AlertCard(alert: UserAlertSettings, toastViewModel: ToastViewModel) {
    val context= LocalContext.current
    val viewModel: AlertsViewModel = viewModel()
    var isEnabled by remember { mutableStateOf(alert.enabled) }

    val alertTypeNames = mapOf(
        "LOW_LEVEL" to Pair("Nivel bajo", R.drawable.nivel),
        "EXCESSIVE_CONSUMPTION" to Pair("Consumo excesivo",R.drawable.consumo),
        "FULL_TANK" to Pair("Tanque lleno",R.drawable.deposito_de_agua),
        "HIGH_LEVEL" to Pair("Nivel alto",R.drawable.nivel),
        "MEDIUM_LEVEL" to Pair("Nivel medio",R.drawable.nivel)
    )
    val alertDetail = alertTypeNames[alert.alertType] ?: Pair("Sin título", R.drawable.ic_notifications)  // Valor por defecto
    val alertName = alertDetail.first
    val icon = alertDetail.second



    OutlinedCard(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CustomTheme.cardBorderSecondary),
        colors = CardDefaults.cardColors(containerColor = CustomTheme.cardPrimary)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(alert.title?:"Sin titulo", style = MaterialTheme.typography.bodyLarge.copy(color = CustomTheme.textOnPrimary))
                Text(alertName, style = MaterialTheme.typography.bodyMedium.copy(color = CustomTheme.textOnPrimary))
                Text(alert.message?:"Sin mensaje", style = MaterialTheme.typography.bodySmall.copy(color = CustomTheme.textOnPrimary))

            }
            Spacer(modifier = Modifier.width(16.dp))
            Switch(checked = isEnabled,
                onCheckedChange = {
                    checked->
                    isEnabled=checked
                    val updatedAlert = alert.copy(enabled = checked)
                    viewModel.updateAlert(context, updatedAlert){
                        response->
                        if(response){
                            toastViewModel.showToast("Estatus actualizado", ToastType.INFO)
                        }else{
                            toastViewModel.showToast("No se actualizo el estatus", ToastType.WARNING)

                        }
                    }
                },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White,
                    checkedIconColor = Color.White, checkedTrackColor = CustomTheme.toggleOn, uncheckedThumbColor = Color.White, uncheckedTrackColor = Color.Gray
                ))
        }
    }
}
