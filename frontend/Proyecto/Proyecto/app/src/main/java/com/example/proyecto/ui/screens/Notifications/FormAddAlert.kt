package com.example.proyecto.ui.screens.Notifications

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.proyecto.data.services.AlertApiService
import com.example.proyecto.model.alert.AlertRequest
import com.example.proyecto.ui.components.custom.CustomDropdown
import com.example.proyecto.ui.components.custom.CustomOutlinedTextField
import com.example.proyecto.ui.theme.mainColor
import com.example.proyecto.ui.theme.toogleChecked

@Composable
fun FormAddAlert(
    alertType: String,
    title: String,
    unit: String,
    threshold: String,
    showForm: Boolean,
    onDismiss: () -> Unit,
){
    var alertType by remember { mutableStateOf(alertType) }
    var enable by remember { mutableStateOf(false) }
    var threshold: String by remember { mutableStateOf(threshold) }
    var unit by remember { mutableStateOf(unit) }
    val context=LocalContext.current
    val alertTypeIds = mapOf(
        "Nivel bajo" to "LOW_LEVEL",
        "Consumo excesivo" to "EXCESSIVE_CONSUMPTION",
        "Tanque lleno" to "FULL_TANK",
        "Nivel alto" to "HIGH_LEVEL",
        "Nivel medio" to "MEDIUM_LEVEL"
    )
    val alertTypeNames = alertTypeIds.keys.toList()
    var isLoading by remember { mutableStateOf(false) }

    val message= if(unit == "%") "Notificar al ${threshold}${unit} de capacidad" else "Notificar a los ${threshold}${unit} consumidos"


    if(showForm){
        AlertDialog(
            onDismissRequest = {if (!isLoading) onDismiss()},
            properties = DialogProperties(dismissOnClickOutside = !isLoading),
            title = {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainColor
                )
            },
            text = {
                LazyColumn() {
                    item {
                        Text(text = "Modifica tu alerta",
                            fontSize = 16.sp)

                        Spacer(modifier = Modifier.padding(16.dp))
                    }

                    item {
                        CustomDropdown(
                            options = alertTypeNames,
                            label = "Tipo de alerta",
                            selectedOption = alertType,
                            onOptionSelected = {alertType=it},
                            whiteBg = true
                        )

                        Spacer(modifier = Modifier.padding(16.dp))
                    }


                    item {
                        CustomOutlinedTextField(
                            label = "Umbral de la alerta",
                            value = threshold.toString(),
                            onValueChange = {threshold=it},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            whiteBg = true

                        )
                    }

                    item {
                        CustomOutlinedTextField(
                            value = unit,
                            label = "Unidad del umbral",
                            readOnly = true,
                            whiteBg = true
                        )
                    }

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Habilitar alerta",
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Switch(
                                checked = enable,
                                onCheckedChange = { enable = it },
                                enabled = !isLoading,
                                colors = SwitchDefaults.colors(
                                    checkedIconColor = Color.White, checkedTrackColor = toogleChecked, uncheckedThumbColor = Color.White, uncheckedTrackColor = Color.Gray
                                )
                            )
                        }
                    }



                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (alertType.isNotBlank() && threshold.isNotBlank()){
                            isLoading=true
                            val userAlertSettings = AlertRequest(
                                alertType= alertTypeIds[alertType] ?: alertType,
                                enabled = enable,
                                threshold = threshold.toFloat(),
                                unit = unit,
                                title = title,
                                message = message
                            )
                            AlertApiService.create(userAlertSettings, context){
                                response->
                                if(response!=null){
                                    Toast.makeText(
                                        context,
                                        "Se ha agregado la alerta",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }else{
                                    Toast.makeText(
                                        context,
                                        "Error al guardar la alerta",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            onDismiss()

                        }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mainColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar", color = mainColor)
                }
            }

        )
    }

}

