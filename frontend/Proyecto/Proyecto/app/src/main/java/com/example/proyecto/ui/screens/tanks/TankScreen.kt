package com.example.proyecto.ui.screens.tanks

import androidx.compose.foundation.layout.
*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.R
import com.example.proyecto.ui.components.custom.ExpandableInfoCard
import com.example.proyecto.ui.components.layout.ColumnLayout
import com.example.proyecto.ui.components.layout.RowTitle
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.TankViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TankScreen(navController: NavHostController, viewModel: TankViewModel = viewModel(), toastViewModel: ToastViewModel) {
    var showModal by remember { mutableStateOf(false) }
    var tankName by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var fillType by remember { mutableStateOf("") }
    val tanks = viewModel.tanks.value
    val context=LocalContext.current
    val isLoading = viewModel.isLoading.value



    LaunchedEffect(Unit) {
        viewModel.loadTanks(context)

    }

    ColumnLayout {
        RowTitle(navController = navController, "Tanques de agua")

        if(isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        else if(tanks.isEmpty()){
            Text(
                text = "Sin tanques en la lista",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = CustomTheme.textOnPrimary,
                )

        }else{
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(tanks) { index, tank ->
                    ExpandableInfoCard(
                        name = tank.tankName?:"no name",
                        imagePainter = painterResource(id = R.drawable.deposito_de_agua),
                        capacity=tank.capacity,
                        tank=tank,
                        toastViewModel = toastViewModel
                    )
                }
            }
        }
        Button(onClick = {
            navController.navigate("formAddTank")

        },
            colors = ButtonDefaults.buttonColors(containerColor = CustomTheme.normalButton, contentColor = CustomTheme.textPrimary ),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) { Text("Agregar tanque")}

    }
}




@Composable
fun TankScreenPreview(navController: NavHostController, toastViewModel: ToastViewModel) {
    Surface(color = CustomTheme.background) {
        TankScreen(navController=navController, toastViewModel = toastViewModel)
    }
}