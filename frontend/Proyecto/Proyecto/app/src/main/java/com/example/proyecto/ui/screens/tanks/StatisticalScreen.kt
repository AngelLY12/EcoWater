package com.example.proyecto.ui.screens.tanks


import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.ui.components.layout.ColumnLayout
import com.example.proyecto.ui.components.layout.RowTitle
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.TankFillingViewModel
import com.example.proyecto.ui.viewModels.WaterTankViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale


@Preview
@Composable
fun StatisticalTankScreenPreview2() {
    val navController = rememberNavController()
    Surface(color = Color(0xFF083257), modifier = Modifier.fillMaxSize()) {
        StatisticalTank(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticalTank(
    navController: NavHostController,
    viewModel: WaterTankViewModel = viewModel(),
    viewModel2: TankFillingViewModel = viewModel()
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    val allLevels = viewModel.levelsListState.value
    val isLoading = viewModel.isLoading.value

    val allFilling = viewModel2.fillingsListState.value
    val isLoadingFilling = viewModel2.isLoading.value

    val totalWaterLevel = allLevels.mapNotNull { it.waterLevel }.sum()
    val formattedTotal = String.format("%.2f", totalWaterLevel)

    val totalHours = allFilling.mapNotNull { it.finishedDate?.time }
        .map { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }
        .sorted() // Ordenamos las fechas para que estén en orden ascendente
        .zipWithNext { prev, next ->
            java.time.Duration.between(prev, next).toHours()
        }
        .sum()

    // Cargar los datos automáticamente al iniciar con la fecha de hoy
    LaunchedEffect(Unit) {
        val today = Date()
        selectedDate = today
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = formatter.format(today)
        viewModel.loadLevelsMainByDate(context, formattedDate)
    }

    // Cargar los datos automáticamente al iniciar con la fecha de hoy
    LaunchedEffect(Unit) {
        val today = Date()
        selectedDate = today
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = formatter.format(today)
        viewModel2.loadMainTankFillingsByDate(context, formattedDate)
    }

    val filteredLevels = allLevels
    val tanksFilling = allFilling

    ColumnLayout {

        RowTitle(navController, "Estadisticas")

        // Cuando el usuario selecciona una fecha, llamamos al ViewModel para cargar los niveles de ese día
        DateSelector { date ->
            selectedDate = date
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = formatter.format(date)
            viewModel.loadLevelsMainByDate(context, formattedDate)
            viewModel2.loadMainTankFillingsByDate(context, formattedDate)
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))

        if (isLoading) {
            Text("Cargando...", color = CustomTheme.textOnPrimary)
        } else if (filteredLevels.isNotEmpty()) {
            TableHead(title = "Consumido (litros/horas)", subTitle = "$formattedTotal litros")
            LevelsTable(filteredLevels)
        } else if (selectedDate != null) {
            Text("Sin registros para esta fecha.", color =  CustomTheme.textOnPrimary)
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))

        if (isLoadingFilling) {
            Text("Cargando...", color =  CustomTheme.textOnPrimary)
        } else if (tanksFilling.isNotEmpty()) {
            TableHead(title = "Llenado (litros/horas)", subTitle = "$totalHours horas")
            TankFillingBarChart(tanksFilling)
        } else if (selectedDate != null) {
            Text("Sin registros para esta fecha.", color =  CustomTheme.textOnPrimary)
        }
    }
}


@Composable
fun StatisticalTankScreenPreview(navController: NavHostController) {
    Surface(color = CustomTheme.background, modifier = Modifier.fillMaxSize()) {
        StatisticalTank(navController = navController)
    }
}