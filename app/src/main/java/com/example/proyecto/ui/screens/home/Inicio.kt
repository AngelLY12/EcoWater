package com.example.proyecto.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.R
import com.example.proyecto.ui.components.BottomNavigationBar
import com.example.proyecto.ui.theme.chartColor
import com.example.proyecto.ui.viewModels.WaterTankViewModel
import com.example.proyecto.utils.GlobalEvent
import com.example.proyecto.utils.GlobalEvent.AuthEvent
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CapacityGraphHeader(modifier: Modifier = Modifier) {
    val currentDate = LocalDate.now()
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", Locale("es", "ES"))
    val dayOfWeek = currentDate.format(dayOfWeekFormatter)

    Column(
        modifier = modifier
    ) {
        Text(
            text = dayOfWeek.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EcoWaterScreen(navController: NavHostController, viewModel: WaterTankViewModel = viewModel()) {
    val context = LocalContext.current
    val levels = viewModel.levelsListState.value
    val isLoading = viewModel.isLoading.value
    LaunchedEffect(Unit) {
        viewModel.loadLevels(context)
    }
    LaunchedEffect(Unit) {
        viewModel.loadData(context)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = Color.Transparent

    ) {
            innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {



            item{
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp,bottom = 16.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),
                            clip = false
                        ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = chartColor)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Top
                        ) {
                            IconButton(

                                onClick = {}
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_notifications),
                                    contentDescription = "Notificaciones",
                                    tint = Color.White,
                                    modifier = Modifier.clickable {
                                        navController.navigate("notfTank")
                                    }
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.eco_water_title),
                                    contentDescription = "EcoWater",
                                    modifier = Modifier.height(60.dp)
                                )
                            }
                        }


                        TankInfo(
                            viewModel = viewModel,
                        )

                        // Botón Añadir como texto con hipervínculo dentro del Card
                        Text(
                            text = "Ver tanques",
                            fontSize = 18.sp,
                            color = Color.White,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    navController.navigate("addTank")
                                }
                                .padding(8.dp)
                        )
                    }
                    //Divider(color = Color.White, thickness = 3.dp)
                }
            }


            item{
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )
            }
            item{
                StatCards()

            }


            item{
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(top=24.dp)
                )
            }



            item{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    CapacityGraphHeader(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        DailyChart(levels = levels)
                    }
                }
            }



        }
    }

}

@Composable
fun EcoWaterScreenPreview(navController: NavHostController) {
    Surface(color = Color(0xFF083257), modifier = Modifier.fillMaxSize()) {
        EcoWaterScreen(navController=navController)
    }
}

