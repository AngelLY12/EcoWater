package com.example.proyecto.ui.screens.devices

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.model.device.DeviceRequest
import com.example.proyecto.model.device.DeviceType
import com.example.proyecto.model.tank.Tank
import com.example.proyecto.ui.components.custom.CustomDropdown
import com.example.proyecto.ui.components.custom.CustomOutlinedTextField
import com.example.proyecto.ui.components.layout.ColumnLayout
import com.example.proyecto.ui.components.layout.RowTitle
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.BluetoothViewModel
import com.example.proyecto.ui.viewModels.TankViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel
import com.google.gson.Gson

@Composable
fun DeviceSetupScreen(
    navController: NavHostController,
    viewModel: BluetoothViewModel,
    toastViewModel: ToastViewModel
) {
    val deviceName = remember { mutableStateOf("") }
    val deviceType = remember { mutableStateOf(DeviceType.SENSOR_PROXIMIDAD) }
    val deviceLocation = remember { mutableStateOf("") }
    val deviceTypes = DeviceType.values().toList()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("setupDevice", Context.MODE_PRIVATE)
    val tankViewModel: TankViewModel= viewModel()

    LaunchedEffect(Unit) {
        tankViewModel.loadTanks(context)
    }

    val tanks = tankViewModel.tanks.value

    var selectedTank by remember { mutableStateOf<Tank?>(null) }

    Surface(color = CustomTheme.background) {
        ColumnLayout {
            RowTitle(navController=navController,"Configura tu dispositivo")

            CustomOutlinedTextField(
                label = "Nombre del dispositivo",
                value = deviceName.value,
                onValueChange = { deviceName.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

            )
            Spacer(modifier = Modifier.height(8.dp))

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

            }
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                CustomDropdown(
                    options = tanks,
                    label = "Tanque del dispositivo",
                    selectedOption = selectedTank,
                    onOptionSelected = { selected ->
                        selectedTank = selected
                        // Aquí puedes hacer lo que necesites con el tanque seleccionado

                        Log.d("Dropdown", "ID del tanque seleccionado: ${selected.tankId}")
                    },
                    optionToText = { option ->
                        option.tankName.toString()
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))






            // Ubicación del dispositivo
            CustomOutlinedTextField(
                label = "Ubicación del dispositivo",
                value = deviceLocation.value,
                onValueChange = { deviceLocation.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

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
                        ssid = "",
                        tank = selectedTank
                    )
                    val json = gson.toJson(data)
                    sharedPreferences.edit() { putString("device_data", json) }
                    navController.navigate("wifiSetup")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CustomTheme.normalButton, contentColor = CustomTheme.textPrimary)
            ) {
                Text("Guardar y continuar", fontWeight = FontWeight.Bold, color = CustomTheme.textPrimary)
            }
        }

    }
}

