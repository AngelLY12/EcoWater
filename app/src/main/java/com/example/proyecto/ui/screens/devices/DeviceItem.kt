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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto.model.models.Device
import com.example.proyecto.model.models.DeviceRequest
import com.example.proyecto.model.models.DeviceType
import com.example.proyecto.ui.components.EditDropdownModal
import com.example.proyecto.ui.components.EditFieldModal
import com.example.proyecto.ui.viewModels.DeviceViewModel
import com.example.proyecto.ui.viewModels.TankViewModel

@Composable
fun DeviceItem(device: Device) {
    var showEditNameModal by remember { mutableStateOf(false) }
    var showEditLocationModal by remember { mutableStateOf(false) }
    var showEditDeviceTypeModal by remember { mutableStateOf(false) }
    var deviceEdit by remember { mutableStateOf(device) }
    val context= LocalContext.current
    val viewModel: DeviceViewModel = viewModel()
    val deviceTypes = DeviceType.values().toList()


    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                modifier = Modifier.fillMaxWidth()
                    .clickable{showEditNameModal=true},
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

            if (showEditNameModal) {
                EditFieldModal(
                    showModal = showEditNameModal,
                    title = "Editar Nombre",
                    initialValue = device.deviceName!!,
                    onDismiss = { showEditNameModal = false },
                    onConfirm = { newName ->
                        deviceEdit = device.copy(deviceName = newName)
                        showEditNameModal = false
                        viewModel.updateDevice(context,deviceEdit)
                    },
                    label = "Nombre del dispositivo"
                )
            }

            // Titulo de propiedades
            Text(
                text = "PROPIEDADES",
                fontSize = 20.sp,
                color = Color.Gray
            )


            // Colocado en
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable{showEditLocationModal=true},
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

            if (showEditLocationModal) {
                EditFieldModal(
                    showModal = showEditLocationModal,
                    title = "Editar lugar",
                    initialValue = device.deviceLocation!!,
                    onDismiss = { showEditLocationModal = false },
                    onConfirm = { newLocation ->
                        deviceEdit = device.copy(deviceLocation = newLocation)
                        showEditLocationModal = false
                        viewModel.updateDevice(context,deviceEdit)
                    },
                    label = "Lugar del dispositivo"
                )
            }
            // Tipo de dispositivo
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable{showEditDeviceTypeModal=true},
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

            if (showEditDeviceTypeModal) {
                EditDropdownModal(
                    showModal = showEditDeviceTypeModal,
                    title = "Editar tipo de dispositivo",
                    options = deviceTypes,
                    selectedOption = device.deviceType,
                    optionToText = { option ->
                        option.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase()} },
                    onDismiss = { showEditDeviceTypeModal = false },
                    onConfirm = { newDeviceType ->
                        deviceEdit = device.copy(deviceType = newDeviceType)
                        showEditDeviceTypeModal = false
                        viewModel.updateDevice(context, deviceEdit)
                    }
                )
            }

            // Eliminar y Divider
            Text(
                text = "Eliminar",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.clickable{
                    viewModel.deleteDevice(device.deviceId!!,context)
                }
            )
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}
