package com.example.proyecto.ui.screens.tanks

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.ui.viewModels.TankViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TankScreen(navController: NavHostController, viewModel: TankViewModel = viewModel()) {
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






    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding( top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
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
                text = "Tanques de agua",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

        if(isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        else if(tanks.isEmpty()){
            Text(
                text = "Sin tanques en la lista",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                )

        }else{
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(tanks) { index, tank ->
                    TankItem(tank = tank)
                }
            }
        }
        Button(onClick = {
            navController.navigate("formAddTank")

        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF083257) ),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) { Text("Agregar tanque")}

    }
}




@Composable
fun TankScreenPreview(navController: NavHostController) {
    Surface(color = Color(0xFF083257)) {
        TankScreen(navController=navController)
    }
}