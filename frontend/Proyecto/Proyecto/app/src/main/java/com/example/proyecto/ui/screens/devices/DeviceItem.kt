package com.example.proyecto.ui.screens.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.proyecto.model.device.Device
import com.example.proyecto.model.device.DeviceType
import com.example.proyecto.ui.components.custom.DeleteCardAlert
import com.example.proyecto.ui.components.custom.EditDropdownModal
import com.example.proyecto.ui.components.custom.EditFieldModal
import com.example.proyecto.ui.components.custom.RowItem
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.DeviceViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun DeviceItem(device: Device,  toastViewModel: ToastViewModel) {
    var showEditNameModal by remember { mutableStateOf(false) }
    var showEditLocationModal by remember { mutableStateOf(false) }
    var showEditDeviceTypeModal by remember { mutableStateOf(false) }
    var showDeleteDialog by remember {mutableStateOf(false)}
    val showDeviceSSID by remember { mutableStateOf(false) }
    var deviceEdit by remember { mutableStateOf(device) }
    val context= LocalContext.current
    val viewModel: DeviceViewModel = viewModel()
    val deviceTypes = DeviceType.values().toList()


    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CustomTheme.cardBorderSecondary),
        colors = CardDefaults.outlinedCardColors(containerColor = CustomTheme.cardPrimary)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            RowItem(onClick = {showEditNameModal=true}, item = device,valueText = { it.deviceName!!.toString() } ,title = "Nombre", unit = "")

            Divider(color = CustomTheme.textOnPrimary, thickness = 1.dp,
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
                        viewModel.updateDevice(context,deviceEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Dispositivo actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar dispositivo", ToastType.WARNING)

                            }
                        }
                    },
                    label = "Nombre del dispositivo"
                )
            }

            Text(
                text = "PROPIEDADES",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomTheme.textOnSecondary,
                modifier = Modifier.padding(top=8.dp, bottom = 8.dp)
            )



            RowItem(onClick = {showEditLocationModal=true}, item = device,valueText = { it.deviceLocation?: "No location" } ,title = "Colocado en ${if (device.deviceType!!.toString() == "SENSOR_PROXIMIDAD") "Tinaco" else "Tubería"}", unit = "")

            Divider(color = CustomTheme.textOnPrimary, thickness = 1.dp,)

            if (showEditLocationModal) {
                EditFieldModal(
                    showModal = showEditLocationModal,
                    title = "Editar lugar",
                    initialValue = device.deviceLocation!!,
                    onDismiss = { showEditLocationModal = false },
                    onConfirm = { newLocation ->
                        deviceEdit = device.copy(deviceLocation = newLocation)
                        showEditLocationModal = false
                        viewModel.updateDevice(context,deviceEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Dispositivo actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar dispositivo", ToastType.WARNING)

                            }
                        }
                    },
                    label = "Lugar del dispositivo"
                )
            }

            RowItem(onClick = {showEditDeviceTypeModal=true}, item = device,valueText = { it.deviceType.toString() ?: "No type" } ,title = "Tipo de dispositivo", unit = "")

            Divider(color = CustomTheme.textOnPrimary, thickness = 1.dp,)

            if (showEditDeviceTypeModal) {
                EditDropdownModal(
                    showModal = showEditDeviceTypeModal,
                    title = "Editar tipo de dispositivo",
                    options = deviceTypes,
                    selectedOption = device.deviceType,
                    optionToText = { option ->
                        option?.name?.replace('_', ' ')?.lowercase()?.replaceFirstChar { it.uppercase()}
                            .toString()
                    },
                    onDismiss = { showEditDeviceTypeModal = false },
                    onConfirm = { newDeviceType ->
                        deviceEdit = device.copy(deviceType = newDeviceType)
                        showEditDeviceTypeModal = false
                        viewModel.updateDevice(context, deviceEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Dispositivo actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar dispositivo", ToastType.WARNING)

                            }
                        }
                    }
                )
            }


            Text(
                text = "Eliminar",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = CustomTheme.textOnPrimary,
                modifier = Modifier.clickable{
                    showDeleteDialog=true
                }.padding(top = 8.dp, bottom = 8.dp)
            )

            DeleteCardAlert(
                title = "¿Estas seguro que quieres eliminar el dispositivo?",
                text = "Esta acción no se puede deshacer y eliminara definitivamente el dispositivo.",
                showDialog = showDeleteDialog,
                onDismiss = { showDeleteDialog = false },
                onConfirm = {
                    showDeleteDialog = false
                    viewModel.deleteDevice(device.deviceId!!, context){
                            success->
                        if(success){
                            toastViewModel.showToast("Dispositivo eliminado con exito", ToastType.SUCCESS)
                        }else{
                            toastViewModel.showToast("Error al eliminar dispositivo", ToastType.ERROR)

                        }
                    }
                }
            )
        }
    }
}
