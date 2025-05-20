package com.example.proyecto.ui.screens.tanks


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.proyecto.model.level.Levels
import com.example.proyecto.model.tankFilling.TankFilling
import com.example.proyecto.ui.components.layout.ColumnLayout
import com.example.proyecto.ui.components.layout.RowTitle
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.TankFillingViewModel
import com.example.proyecto.ui.viewModels.WaterTankViewModel
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Preview
@Composable
fun ReportScreenScreenPreview2() {
    val navController = rememberNavController()
    Surface(color = CustomTheme.background, modifier = Modifier.fillMaxSize()) {
        ReportScreen(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    navController: NavHostController,
    viewModel: WaterTankViewModel = viewModel(),
    viewModel2: TankFillingViewModel = viewModel()
) {
    val context = LocalContext.current
    val isLoading = viewModel.isLoading.value

    // Estado para saber si la fecha se ha cargado
    val firstMeasurementDate = viewModel.firstMeasurementDateState.value

    // Esperamos que firstMeasurementDate esté disponible antes de proceder
    LaunchedEffect(firstMeasurementDate) {
        if (firstMeasurementDate == null) {
            viewModel.loadFirstMeasurementDate(context)
        }
    }

    // Si aún no tenemos la fecha, no cargamos la lista de meses
    if (firstMeasurementDate == null) {
        // Mostrar cargando o alguna UI mientras esperamos la fecha
        return Text("Cargando la fecha de medición...", color = CustomTheme.textOnPrimary)
    }

    // Si ya tenemos la fecha, la usamos como startMonth
    val startMonth = YearMonth.from(
        firstMeasurementDate.dateMeasurement?.toInstant()?.atZone(ZoneId.systemDefault())
            ?.toLocalDate()
    )
    val currentMonth = YearMonth.now()
    val monthsToShow = getMonthsBetween(startMonth, currentMonth)

    ColumnLayout {

        RowTitle(navController, "Reportes")


        // Mostrar los meses disponibles para el reporte
        monthsToShow.forEach { month ->
            val coroutineScope = rememberCoroutineScope()

            ReportMonthCard(month = month) { selectedMonth ->
                coroutineScope.launch {
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, selectedMonth.year)
                    calendar.set(Calendar.MONTH, selectedMonth.monthValue - 1)
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)

                    val startDate =
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                    val startHour = "00:00"

                    calendar.set(
                        Calendar.DAY_OF_MONTH,
                        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                    )
                    calendar.set(Calendar.HOUR_OF_DAY, 23)
                    calendar.set(Calendar.MINUTE, 59)
                    calendar.set(Calendar.SECOND, 59)
                    val endDate =
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                    val endHour = "23:59"

                    viewModel.loadLevelsMainByDateTime(
                        context,
                        startDate,
                        endDate,
                        startHour,
                        endHour
                    )
                    viewModel2.loadMainTankFillingsByDateTime(
                        context,
                        startDate,
                        endDate,
                        startHour,
                        endHour
                    )

                    delay(1500) // Simula espera de carga

                    val levels = viewModel.levelsListState.value
                    val fillings = viewModel2.fillingsListState.value

                    val groupedLevels = levels.groupBy {
                        it.dateMeasurement?.toInstant()?.atZone(ZoneId.systemDefault())
                            ?.toLocalDate() ?: LocalDate.MIN
                    }

                    val groupedFilling = fillings.groupBy {
                        it.finishedDate?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
                            ?: LocalDate.MIN
                    }

                    if (groupedLevels.isNotEmpty() || groupedFilling.isNotEmpty()) {
                        val document =
                            generatePdfWithBothCharts(context, groupedLevels, groupedFilling)

                        val formatter = DateTimeFormatter.ofPattern("MMMM_yyyy", Locale("es", "ES"))
                        val monthFormatted = selectedMonth.format(formatter).replace(" ", "_").replaceFirstChar { it.uppercase() }
                        val fileName = "Consumo_de_agua_${monthFormatted}.pdf"
                        savePdfToDownloads(context, document, fileName)
                    }
                }
            }
        }
    }
}





fun getMonthsBetween(start: YearMonth, end: YearMonth): List<YearMonth> {
    val months = mutableListOf<YearMonth>()
    var current = start
    while (current <= end) {
        months.add(current)
        current = current.plusMonths(1)
    }
    return months
}


@Composable
fun ReportScreenScreenPreview(navController: NavHostController) {
    Surface(color = CustomTheme.background, modifier = Modifier.fillMaxSize()) {
        ReportScreen(navController = navController)
    }
}