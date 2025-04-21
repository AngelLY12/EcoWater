package com.example.proyecto.ui.screens.devices

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.proyecto.ui.components.layout.BottomNavigationBar
import com.example.proyecto.ui.viewModels.BluetoothViewModel
import com.example.proyecto.utils.BluetoothUtils
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.viewModels.ToastViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission")
@Composable
fun DispositivosScreen(navController: NavHostController, viewModel: BluetoothViewModel = hiltViewModel(), onConnected: () -> Unit, toastViewModel: ToastViewModel) {
    val context = LocalContext.current
    val activity = context as Activity

    val devices by remember { derivedStateOf { viewModel.devices } }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms.all { it.value }) {
            viewModel.ensureBluetoothEnabled(activity)
            viewModel.startDiscovery(context)
        }
    }

    LaunchedEffect(Unit) {
        if (!BluetoothUtils.hasPermissions(context)) {
            launcher.launch(BluetoothUtils.REQUIRED_PERMISSIONS)
        } else {
            viewModel.ensureBluetoothEnabled(activity)
            viewModel.ensureLocationEnabled(activity)
            viewModel.startDiscovery(context)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = Color.Transparent

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally

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
                    text = "Dispositivos",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold // Texto en negritas
                    ),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Color.White // Título blanco
                )
            }

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFF64B5F6) // Color más claro para LinearProgressIndicator
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Cargando", tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))

            }

            if (devices.isEmpty()) {
                Text(
                    text = "Buscando dispositivos...",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(start = 0.dp),
                    color = Color.White
                )
            } else {
                Column(Modifier.padding(16.dp)) {
                    Text("Selecciona tu dispositivo", fontWeight = FontWeight.Bold)

                    LazyColumn() {
                        itemsIndexed(devices) { _, device ->
                            DeviceItem(
                                device.name ?: device.address,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.connectToDevice(device) { success ->
                                            if (success) {
                                                toastViewModel.showToast("Conectado a ${device.name}",
                                                    ToastType.SUCCESS)
                                                viewModel.selectedDevice = device
                                                onConnected()
                                            } else {
                                                toastViewModel.showToast("Error al conectar",
                                                    ToastType.ERROR)
                                            }
                                        }
                                    }
                            )
                                /*
                                Column(Modifier.padding(16.dp)) {
                                    Text("Nombre: ${device.name ?: "Desconocido"}")
                                    Text("MAC: ${device.address}")
                                }

                                 */

                        }
                    }


                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = { /* TODO: Implementar lógica para problemas */ }) {
                        Text(text = "¿Tienes problemas?", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}


@Composable
fun DeviceItem(deviceName: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(24.dp), // Bordes ovalados
        color = Color(0xFF083257), // Color de fondo #083257
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        border = BorderStroke(1.dp, Color.White) // Margen blanco
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre checkbox y texto
            Text(
                text = deviceName,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold), // Texto más grueso
                modifier = Modifier.padding(start = 0.dp), // Eliminar padding innecesario
                color = Color.White // Texto blanco
            )
        }
    }
}

@Composable
fun DispositivosScreenPreview(navController: NavHostController, viewModel: BluetoothViewModel = hiltViewModel(), onConnected: () -> Unit, toastViewModel: ToastViewModel) {
    Surface(color = Color(0xFF083257)) { // Color azul oscuro #083257
        DispositivosScreen(navController=navController, viewModel = viewModel, onConnected, toastViewModel = toastViewModel)
    }
}