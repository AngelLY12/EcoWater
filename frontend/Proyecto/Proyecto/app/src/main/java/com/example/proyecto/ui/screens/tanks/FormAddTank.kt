package com.example.proyecto.ui.screens.tanks

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.data.services.TankApiService
import com.example.proyecto.model.tank.Tank
import com.example.proyecto.ui.components.layout.BottomNavItem
import com.example.proyecto.ui.components.custom.CustomDropdown
import com.example.proyecto.ui.components.custom.CustomOutlinedTextField
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.components.layout.ColumnLayout
import com.example.proyecto.ui.components.layout.RowTitle
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun FormAddTank(navController: NavHostController, toastViewModel: ToastViewModel= viewModel()){
    var tankName by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    val optionsFillType = listOf("MANUAL", "AUTOMATICO", "BOMBA", "RED_AGUA", "CISTERNA")
    var fillType by remember { mutableStateOf<String?>(null) }
    val optionsMain = listOf(true, false)
    var isMain by remember { mutableStateOf<Boolean?>(null) }
    val context= LocalContext.current


    Surface(color = CustomTheme.background) {
        ColumnLayout {
            RowTitle(navController=navController,"Nuevo tanque de agua")

            Column(modifier = Modifier.padding(8.dp)) {
                CustomOutlinedTextField(
                    label = "Nombre del Tinaco",
                    value = tankName,
                    onValueChange = {tankName=it},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    label = "Capacidad",
                    value = capacity,
                    onValueChange = {capacity=it},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )



                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    label = "Altura",
                    value = height,
                    onValueChange = {height=it},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomDropdown(
                    options = optionsFillType,
                    label = "Tipo de llenado",
                    selectedOption = fillType,
                    onOptionSelected = { fillType = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
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
                colors = ButtonDefaults.buttonColors(containerColor = CustomTheme.normalButton, contentColor = CustomTheme.textPrimary ),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) { Text("Guardar")}
        }
    }

}