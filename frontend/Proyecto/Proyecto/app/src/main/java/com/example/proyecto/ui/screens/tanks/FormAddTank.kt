package com.example.proyecto.ui.screens.tanks

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.navigation.NavHostController
import com.example.proyecto.data.services.TankApiService
import com.example.proyecto.model.models.Tank
import com.example.proyecto.ui.components.BottomNavItem
import com.example.proyecto.ui.components.CustomDropdown
import com.example.proyecto.ui.components.CustomOutlinedTextField
import com.example.proyecto.ui.components.ToastType
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun FormAddTank(navController: NavHostController, toastViewModel: ToastViewModel){
    var tankName by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    val optionsFillType = listOf("MANUAL", "AUTOMATICO", "BOMBA", "RED_AGUA", "CISTERNA")
    var fillType by remember { mutableStateOf<String?>(null) }
    val optionsMain = listOf(true, false)
    var isMain by remember { mutableStateOf<Boolean?>(null) }
    val context= LocalContext.current


    Surface(color = Color(0xFF083257)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding( top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
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
                Text(
                    text = "Nuevo tanque de agua",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text("Nombre del tinaco",color = Color.White, fontSize = 16.sp)
                CustomOutlinedTextField(
                    label = "Ingresa el nombre del Tinaco",
                    value = tankName,
                    onValueChange = {tankName=it}
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text("Capacidad",color = Color.White, fontSize = 16.sp)
                CustomOutlinedTextField(
                    label = "Ingresa la Capacidad",
                    value = capacity,
                    onValueChange = {capacity=it},
                    )



                Spacer(modifier = Modifier.height(8.dp))

                Text("Altura",color = Color.White, fontSize = 16.sp)
                CustomOutlinedTextField(
                    label = "Ingresa la altura",
                    value = height,
                    onValueChange = {height=it},
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomDropdown(
                    options = optionsFillType,
                    label = "Tipo de llenado",
                    selectedOption = fillType,
                    onOptionSelected = { fillType = it }
                )
                CustomDropdown(
                    options = optionsMain,
                    label = "¿Tinaco principal?",
                    selectedOption = isMain,
                    onOptionSelected = { isMain = it },
                    optionToText = { if (it) "Sí" else "No" }
                )
            }
            Button(onClick = {
                if (tankName.isNotBlank() && capacity.isNotBlank() && height.isNotBlank() && fillType != null && isMain != null) {
                    val tank = Tank(
                        tankName = tankName,
                        capacity = capacity.toFloat(),
                        fillingType = fillType!!,
                        tankHeight = height.toFloat(),
                        isMain = isMain!!
                    )

                    TankApiService.create(tank, context){response->
                        if(response!=null){
                            toastViewModel.showToast("Tanque agregado exitosamente", ToastType.SUCCESS)
                            navController.navigate(BottomNavItem.Home.route)
                        }else{
                            toastViewModel.showToast("Error al agregar el tanque", ToastType.ERROR)

                        }

                    }
                } else {
                    toastViewModel.showToast("Completa todos los campos", ToastType.ERROR)
                }

            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF083257) ),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) { Text("Guardar")}
        }
    }

}