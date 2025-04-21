package com.example.proyecto.ui.screens.devices

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyecto.data.services.DeviceApiService
import com.example.proyecto.model.device.DeviceRequest
import com.example.proyecto.ui.components.layout.BottomNavItem
import com.example.proyecto.ui.viewModels.BluetoothViewModel
import com.google.gson.Gson
import androidx.core.content.edit
import com.example.proyecto.ui.components.custom.CustomOutlinedTextField
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun WifiCredentialsForm( navController: NavHostController,viewModel: BluetoothViewModel, toastViewModel: ToastViewModel) {
    val context= LocalContext.current
    val ssid = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val gson = Gson()
    val sharedPreferences = context.getSharedPreferences("setupDevice", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("device_data", null)
    var passwordVisible by remember { mutableStateOf(false) }
    var passworConfirmVisible by remember { mutableStateOf(false) }

    val deviceRequest = json?.let {
        gson.fromJson(it, DeviceRequest::class.java)
    }

    Column(modifier = Modifier
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
                "Credenciales Wi-Fi",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        CustomOutlinedTextField(
            label = "SSID",
            value = ssid.value,
            onValueChange = { ssid.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified)

        )
        CustomOutlinedTextField(
            label = "Contraseña",
            value = password.value,
            onValueChange = { password.value = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

        Button(
            onClick = {
                val updatedDevice = deviceRequest?.copy(ssid = ssid.value)
                if (updatedDevice != null) {
                    DeviceApiService.create(updatedDevice, context){
                        response->
                        if(response!=null){
                            viewModel.sendCredentials(ssid.value, password.value)
                            sharedPreferences.edit() { remove("device_data") }
                            navController.navigate(BottomNavItem.Devices.route)
                            toastViewModel.showToast("Dispositivo agregado correctamente",
                                ToastType.SUCCESS)
                        }else{
                            toastViewModel.showToast("Tanque: ${updatedDevice.tank}",
                                ToastType.ERROR)
                        }
                    }

                } else {
                    toastViewModel.showToast("Error dispositivo no encontrado",
                        ToastType.ERROR)                }
                navController.navigate(BottomNavItem.Devices.route)
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF083257))

        ) {
            Text("Enviar")
        }
    }
}
