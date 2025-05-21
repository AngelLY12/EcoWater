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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Preview
@Composable
fun ConsumeTankScreenPreview2() {
    val navController = rememberNavController()
    Surface(color = Color(0xFF083257), modifier = Modifier.fillMaxSize()) {
        ConsumeTank(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumeTank(
    navController: NavHostController,
    viewModel: WaterTankViewModel = viewModel(),
    viewModel2: TankFillingViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedDate: Date? = Date() // Obtiene la fecha y hora actuales

    val allLevels = viewModel.levelsListState.value
    val isLoading = viewModel.isLoading.value

    val allFilling = viewModel2.fillingsListState.value
    val isLoadingFilling = viewModel2.isLoading.value

    val dateFormatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
    val totalWaterLevel = allLevels.mapNotNull { it.waterLevel }.sum()
    val formattedTotal = String.format("%.2f", totalWaterLevel)

    // Cargar los datos automáticamente al iniciar con la fecha de hoy
    LaunchedEffect(Unit) {
        val calendar = Calendar.getInstance()
        val now = calendar.time // Fecha y hora actual

        calendar.add(Calendar.HOUR_OF_DAY, -2) // Restar 2 horas
        val twoHoursAgo = calendar.time // Fecha y hora de hace 2 horas

        // Formatear fecha y hora en `yyyy-MM-dd HH:mm`
        val formatterDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = formatterDate.format(now)

        val formatterHour = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startHour = formatterHour.format(twoHoursAgo) // Hora inicial (hace 2 horas)
        val endHour = formatterHour.format(now) // Hora final (actual)

        // Cargar datos de niveles en el rango de tiempo
        viewModel.loadLevelsMainByDateTime(context, formattedDate, startHour, endHour)

        // Cargar datos de llenado de tanques en el rango de tiempo
        viewModel2.loadMainTankFillingsByDateTime(context, formattedDate, startHour, endHour)
    }

    val filteredLevels = allLevels
    val tanksFilling = allFilling

    ColumnLayout {
        RowTitle(navController = navController, "Consumo")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedDate?.let { dateFormatter.format(it) } ?: "Sin fecha",
                style = MaterialTheme.typography.bodyLarge,
                color = CustomTheme.textOnPrimary,
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        if (isLoading) {
            Text("Cargando...", color = CustomTheme.textOnSecondary)
        } else if (filteredLevels.isNotEmpty()) {
            TableHead(title = "$formattedTotal litros", subTitle = "Consumidos")
            LevelsTable(filteredLevels)
        } else if (selectedDate != null) {
            Text("Sin registros para esta fecha.", color = CustomTheme.textOnPrimary)
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        if (isLoadingFilling) {
            Text("Cargando...", color = CustomTheme.textOnSecondary)
        } else if (tanksFilling.isNotEmpty()) {
            TableHead(title = "2 horas", subTitle = "Llenando")
            TankFillingBarChart(tanksFilling)
        } else if (selectedDate != null) {
            Text("Sin registros para esta fecha.", color = CustomTheme.textOnPrimary)
        }
    }
}

@Composable
fun ConsumeTankScreenPreview(navController: NavHostController) {
    Surface(color = CustomTheme.background, modifier = Modifier.fillMaxSize()) {
        ConsumeTank(navController = navController)
    }
}