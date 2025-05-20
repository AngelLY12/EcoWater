package com.example.proyecto.ui.screens.tanks

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
import com.example.proyecto.model.tank.Tank
import com.example.proyecto.ui.components.custom.DeleteCardAlert
import com.example.proyecto.ui.components.custom.EditDropdownModal
import com.example.proyecto.ui.components.custom.EditFieldModal
import com.example.proyecto.ui.components.custom.RowItem
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.TankViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun TankItem(tank: Tank,  toastViewModel: ToastViewModel) {
    var showEditNameModal by remember { mutableStateOf(false) }
    var showEditCapacityModal by remember { mutableStateOf(false) }
    var showEditFillingModal by remember { mutableStateOf(false) }
    var showEditHeightgModal by remember { mutableStateOf(false) }
    var showEditMainModal by remember { mutableStateOf(false) }
    var tankEdit by remember { mutableStateOf(tank) }
    val context= LocalContext.current
    val viewModel: TankViewModel = viewModel()
    val optionsMain = listOf(true, false)
    val optionsFillType = listOf("MANUAL", "AUTOMATICO", "BOMBA", "RED_AGUA", "CISTERNA")
    var showDeleteDialog by remember {mutableStateOf(false)}


    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CustomTheme.cardBorderSecondary),
        colors = CardDefaults.outlinedCardColors(containerColor = CustomTheme.cardPrimary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RowItem(onClick = {showEditNameModal=true}, item = tank,valueText = { it.tankName!!.toString() } ,title = "Nombre", unit = "")
            Divider(
                color = CustomTheme.textOnPrimary,
                thickness = 1.dp,
            )
            if (showEditNameModal) {
                EditFieldModal(
                    showModal = showEditNameModal,
                    title = "Editar Nombre",
                    initialValue = tank.tankName!!,
                    onDismiss = { showEditNameModal = false },
                    onConfirm = { newName ->
                        tankEdit = tank.copy(tankName = newName)
                        showEditNameModal = false
                        viewModel.updateTank(context,tankEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Registro actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar", ToastType.WARNING)

                            }
                        }
                    },
                    label = "Nombre del tanque"
                )
            }

            Text(
                text = "CARACTERÍSTICAS",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomTheme.textOnSecondary,
                modifier = Modifier.padding(top=8.dp, bottom = 8.dp)
            )

            RowItem(onClick = {showEditCapacityModal=true}, item = tank, valueText = { it.capacity.toString() },title = "Capacidad" ,unit = "L")

            Divider(color = CustomTheme.textOnPrimary, thickness = 1.dp)

            if (showEditCapacityModal) {
                EditFieldModal(
                    showModal = showEditCapacityModal,
                    title = "Editar Capacidad",
                    initialValue = tank.capacity.toString(),
                    onDismiss = { showEditCapacityModal = false },
                    onConfirm = { newCapacity ->
                        tankEdit = tank.copy(capacity = newCapacity.toFloat())
                        showEditCapacityModal = false
                        viewModel.updateTank(context,tankEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Registro actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar", ToastType.WARNING)

                            }
                        }

                    },
                    label = "Capacidad del tanque"
                )
            }

            RowItem(onClick = {showEditHeightgModal=true}, item = tank, valueText = { it.tankHeight.toString() },title = "Altura", unit = "Metros")

            Divider(color = CustomTheme.textOnPrimary, thickness = 1.dp)

            if (showEditHeightgModal) {
                EditFieldModal(
                    showModal = showEditHeightgModal,
                    title = "Editar altura",
                    initialValue = tank.tankHeight.toString(),
                    onDismiss = { showEditHeightgModal = false },
                    onConfirm = { newHeight ->
                        tankEdit = tank.copy(tankHeight = newHeight.toFloat())
                        showEditHeightgModal = false
                        viewModel.updateTank(context,tankEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Registro actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar", ToastType.WARNING)

                            }
                        }
                    },
                    label = "Capacidad del tanque"
                )
            }

            RowItem(onClick = {showEditFillingModal=true}, item=tank,valueText = { it.fillingType.toString() }, title = "Tipo de llenado", unit = "")
            Divider(color = CustomTheme.textOnPrimary, thickness = 1.dp,)

            if (showEditFillingModal) {
                EditDropdownModal(
                    showModal = showEditFillingModal,
                    title = "Editar tipo de llenado",
                    options = optionsFillType,
                    selectedOption = tank.fillingType ,
                    optionToText = { it.toString()  },
                    onDismiss = { showEditFillingModal = false },
                    onConfirm = { newType ->
                        tankEdit = tank.copy(fillingType = newType)
                        showEditFillingModal = false
                        viewModel.updateTank(context, tankEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Registro actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar", ToastType.WARNING)

                            }
                        }
                    }
                )

            }

            RowItem(onClick = {showEditMainModal=true}, item=tank,valueText = { it.isMain.toString() } ,title = "Tanque principal", unit = "")

            if (showEditMainModal) {
                EditDropdownModal(
                    showModal = showEditMainModal,
                    title = "Editar tanque principal",
                    options = optionsMain,
                    selectedOption = if (tank.isMain==true) "Sí" else "No" ,
                    optionToText = { if (it == true) "Sí" else "No" },
                    onDismiss = { showEditMainModal = false },
                    onConfirm = { newMain ->
                        val isMainBoolean = when (newMain) {
                            "Sí" -> true
                            "No" -> false
                            else -> null
                        }
                        tankEdit = tank.copy(isMain = isMainBoolean)
                        showEditMainModal = false
                        viewModel.updateTank(context, tankEdit){
                                success->
                            if(success){
                                toastViewModel.showToast("Registro actualizado", ToastType.INFO)
                            }else{
                                toastViewModel.showToast("Error al actualizar", ToastType.WARNING)

                            }
                        }
                    }
                )

            }

            Divider(
                color = CustomTheme.textOnPrimary,
                thickness = 1.dp,
            )
            // Eliminar
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
                title = "¿Estas seguro que quieres eliminar el tanque?",
                text = "Esta acción no se puede deshacer y eliminara completamente el registro.",
                showDialog = showDeleteDialog,
                onDismiss = { showDeleteDialog = false },
                onConfirm = {
                    showDeleteDialog = false
                    viewModel.deleteTank(tank.tankId!!,context){
                            success->
                        if(success.equals("Exito")){
                            toastViewModel.showToast("Tanque eliminado con exito", ToastType.SUCCESS)
                        }else{
                            toastViewModel.showToast("Error al eliminar tanque", ToastType.ERROR)

                        }
                    }

                }
            )


        }
    }


}