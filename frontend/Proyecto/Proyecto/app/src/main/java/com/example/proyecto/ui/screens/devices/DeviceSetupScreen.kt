package com.example.proyecto.ui.screens.devices

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.proyecto.model.models.DeviceRequest
import com.example.proyecto.model.models.DeviceType
import com.example.proyecto.ui.components.CustomDropdown
import com.example.proyecto.ui.components.CustomOutlinedTextField
import com.example.proyecto.ui.viewModels.BluetoothViewModel
import com.google.gson.Gson

@Composable
fun DeviceSetupScreen(
    navController: NavHostController,
    viewModel: BluetoothViewModel
) {
    val deviceName = remember { mutableStateOf("") }
    val deviceType = remember { mutableStateOf(DeviceType.SENSOR_PROXIMIDAD) }
    val deviceLocation = remember { mutableStateOf("") }
    val deviceTypes = DeviceType.values().toList()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("setupDevice", Context.MODE_PRIVATE)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF083257)) // Fondo azul oscuro
            .padding(16.dp).padding( top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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


            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre icono y título
            Text(
                "Configura tu dispositivo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        // Nombre del dispositivo
        CustomOutlinedTextField(
            label = "Nombre del dispositivo",
            value = deviceName.value,
            onValueChange = { deviceName.value = it }
        )

        // Dropdown para tipo de dispositivo
        //var expanded by remember { mutableStateOf(false) }
        Box {
            CustomDropdown(
                options = deviceTypes,
                label = "Tipo de dispositivo",
                selectedOption = deviceType.value,
                onOptionSelected = { selected ->
                    deviceType.value = selected },
                optionToText = { option ->
                    option.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() }
                }
            )
            /*
            CustomOutlinedTextField(
                label = "Tipo de dispositivo",
                value = deviceType.value.name.toString(),
                onClick = { expanded = true },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
                    }
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                deviceTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.name.toString()) },
                        onClick = {
                            deviceType.value = type
                            expanded = false
                        }
                    )
                }
            }

             */
        }

        // Ubicación del dispositivo
        CustomOutlinedTextField(
            label = "Ubicación del dispositivo",
            value = deviceLocation.value,
            onValueChange = { deviceLocation.value = it }
        )

        // Botón de guardar
        Button(
            onClick = {
                val gson = Gson()
                val data = DeviceRequest(
                    device_id = viewModel.selectedDevice?.address ?: "",
                    device_name = deviceName.value,
                    device_type = deviceType.value.toString(),
                    device_location = deviceLocation.value,
                    ssid = ""
                )
                val json = gson.toJson(data)
                sharedPreferences.edit() { putString("device_data", json) }
                navController.navigate("wifiSetup")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF083257))
        ) {
            Text("Guardar y continuar", fontWeight = FontWeight.Bold)
        }
    }
}

