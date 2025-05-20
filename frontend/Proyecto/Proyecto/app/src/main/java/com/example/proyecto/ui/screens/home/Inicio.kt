package com.example.proyecto.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.R
import com.example.proyecto.ui.components.layout.BottomNavigationBar
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.ToastViewModel
import com.example.proyecto.ui.viewModels.WaterTankViewModel
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
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = CustomTheme.textOnPrimary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EcoWaterScreen(navController: NavHostController, viewModel: WaterTankViewModel = viewModel(), toastViewModel: ToastViewModel) {
    val context = LocalContext.current
    val levels = viewModel.levelsListState.value
    val isLoading = viewModel.isLoading.value
    val isRefreshing = viewModel.isLoading.value

    val pullRefresh = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            viewModel.loadLevels(context)
            viewModel.loadData(context)
        }
    )
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
        containerColor = Color.Transparent,

    ) {

            innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefresh)
        ) {
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
                        colors = CardDefaults.cardColors(containerColor = CustomTheme.cardPrimary)
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
                                        tint = CustomTheme.textOnPrimary,
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
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold,
                                            text = "Ec", style = MaterialTheme.typography.displayMedium, color = CustomTheme.textOnPrimary)

                                        Image(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = "Logo O",
                                            modifier = Modifier
                                                .size(34.dp) // ajusta según el tamaño del texto
                                                .padding(horizontal = 2.dp)
                                        )

                                        Text(
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold,
                                            text = "Water", style = MaterialTheme.typography.displayMedium, color = CustomTheme.textOnPrimary)
                                    }
                                }
                            }


                            TankInfo(
                                viewModel = viewModel,
                            )

                            // Botón Añadir como texto con hipervínculo dentro del Card
                            Text(
                                text = "Ver tanques",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomTheme.textOnSecondary,
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
                        color = CustomTheme.cardBorder,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                    )
                }
                item{
                    StatCards(navController)

                }


                item{
                    Divider(
                        color = CustomTheme.cardBorder,
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
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefresh,
                modifier = Modifier
                    .align(Alignment.TopCenter).padding(top = 32.dp)
            )
        }



    }

}

@Composable
fun EcoWaterScreenPreview(navController: NavHostController, toastViewModel: ToastViewModel) {
    Surface(color = CustomTheme.background, modifier = Modifier.fillMaxSize()) {
        EcoWaterScreen(navController=navController, toastViewModel = toastViewModel)
    }
}

