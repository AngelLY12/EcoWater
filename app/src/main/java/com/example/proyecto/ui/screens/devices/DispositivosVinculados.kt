package com.example.proyecto.ui.screens.devices

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.ui.components.BottomNavigationBar
import com.example.proyecto.ui.components.ExpandableInfoCard
import com.example.proyecto.ui.screens.tanks.TankItem
import com.example.proyecto.ui.viewModels.DeviceViewModel
import com.example.proyecto.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LinkedDeviceScreen(navController: NavHostController,viewModel: DeviceViewModel= viewModel()) {

    val context= LocalContext.current
    val devices = viewModel.devices.value
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(Unit) {
        viewModel.loadDevices(context)

    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = Color.Transparent

    ) {
        innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp).padding(innerPadding)
        ) {
            // Botón de retroceso y título
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Dispositivo vinculado",
                    fontSize = 28.sp, // Aumentado a 28.sp
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if(isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            else if(devices.isEmpty()){
                Text(
                    text = "Sin dispositivos en la lista",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White,
                )
            }else{
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(devices) { index, device ->
                        ExpandableInfoCard(
                            name = device.deviceName!!,
                            imagePainter = painterResource(id = R.drawable.device),
                            isConnected = device.connected,
                            device = device
                        )
                    }
                }
            }

            Button(onClick = {
                navController.navigate("deviceList")

            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF083257) ),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) { Text("Agregar Dispositivo")}
        }
    }



}

@Composable
fun LinkedDeviceScreenPreview(navController: NavHostController) {
    Surface(color = Color(0xFF083257)) {
        LinkedDeviceScreen(navController=navController)
    }
}