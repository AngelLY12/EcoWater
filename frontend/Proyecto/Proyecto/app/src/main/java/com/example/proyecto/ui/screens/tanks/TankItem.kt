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
import com.example.proyecto.data.services.TankApiService
import com.example.proyecto.model.models.Tank
import com.example.proyecto.ui.components.EditFieldModal
import com.example.proyecto.ui.viewModels.TankViewModel

@Composable
fun TankItem(tank: Tank, viewModel: TankViewModel = viewModel()) {
    var showEditNameModal by remember { mutableStateOf(false) }
    var showEditCapacityModal by remember { mutableStateOf(false) }
    var showEditFillingModal by remember { mutableStateOf(false) }
    var showEditHeightgModal by remember { mutableStateOf(false) }
    var tankEdit by remember { mutableStateOf(tank) }
    val context= LocalContext.current

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Acción para ver más detalles del tanque */ },
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
                        TankApiService.updateTank(context = context, tank = tankEdit)
                        viewModel.updateTankInList(tankEdit)
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
                    .padding(vertical = 8.dp),
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


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
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

            // Tipo de llenado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
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
            // Eliminar
            Text(
                text = "Eliminar",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }

}