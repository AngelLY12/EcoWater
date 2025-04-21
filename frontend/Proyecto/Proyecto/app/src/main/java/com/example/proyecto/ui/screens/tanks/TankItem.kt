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
import com.example.proyecto.ui.viewModels.TankViewModel

@Composable
fun TankItem(tank: Tank) {
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
        border = BorderStroke(1.dp, Color.White),
        colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFF083257))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Cambiar nombre
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{showEditNameModal=true
                    },
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
                        text = tank.tankName!!,  // Nombre del tanque
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
                    initialValue = tank.tankName!!,
                    onDismiss = { showEditNameModal = false },
                    onConfirm = { newName ->
                        tankEdit = tank.copy(tankName = newName)
                        showEditNameModal = false
                        viewModel.updateTank(context,tankEdit)
                    },
                    label = "Nombre del tanque"
                )
            }

            // Sección de características
            Text(
                text = "CARACTERÍSTICAS",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Capacidad
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable{showEditCapacityModal=true},
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Capacidad",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "${tank.capacity} L",  // Muestra la capacidad
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

            Divider(color = Color.White, thickness = 1.dp)

            if (showEditCapacityModal) {
                EditFieldModal(
                    showModal = showEditCapacityModal,
                    title = "Editar Capacidad",
                    initialValue = tank.capacity.toString(),
                    onDismiss = { showEditCapacityModal = false },
                    onConfirm = { newCapacity ->
                        tankEdit = tank.copy(capacity = newCapacity.toFloat())
                        showEditCapacityModal = false
                        viewModel.updateTank(context,tankEdit)

                    },
                    label = "Capacidad del tanque"
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable{showEditHeightgModal=true},
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Altura",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "${tank.tankHeight} Metros",  // Muestra la capacidad
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

            Divider(color = Color.White, thickness = 1.dp)

            if (showEditHeightgModal) {
                EditFieldModal(
                    showModal = showEditHeightgModal,
                    title = "Editar altura",
                    initialValue = tank.tankHeight.toString(),
                    onDismiss = { showEditHeightgModal = false },
                    onConfirm = { newHeight ->
                        tankEdit = tank.copy(tankHeight = newHeight.toFloat())
                        showEditHeightgModal = false
                        viewModel.updateTank(context,tankEdit)
                    },
                    label = "Capacidad del tanque"
                )
            }

            // Tipo de llenado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable{showEditFillingModal=true},
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tipo de llenado",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = tank.fillingType!!,  // Muestra el tipo de llenado
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
                        viewModel.updateTank(context, tankEdit)
                    }
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable{showEditMainModal=true},
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tanque principal",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "${tank.isMain}",
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
                        viewModel.updateTank(context, tankEdit)
                    }
                )

            }

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            // Eliminar
            Text(
                text = "Eliminar",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.clickable{
                    showDeleteDialog=true
                }
            )

            DeleteCardAlert(
                title = "¿Estas seguro que quieres eliminar el tanque?",
                text = "Esta acción no se puede deshacer y eliminara los dispositivos enlazados y las mediciones.",
                showDialog = showDeleteDialog,
                onDismiss = { showDeleteDialog = false },
                onConfirm = {
                    showDeleteDialog = false
                    viewModel.deleteTank(tank.tankId!!,context)
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