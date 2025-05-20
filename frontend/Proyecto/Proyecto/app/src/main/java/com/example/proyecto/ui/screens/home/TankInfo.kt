package com.example.proyecto.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto.R
import com.example.proyecto.model.level.LevelResponse
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.WaterTankViewModel

@Composable
fun TankInfo(viewModel: WaterTankViewModel = viewModel()) {
    val context = LocalContext.current
    val level = viewModel.levelState.value
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(Unit) {
        viewModel.loadData(context)
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            WaterTankInfo(level)
        }
    }
}

@Composable
fun WaterTankInfo(level: LevelResponse?) {
    val consumido= level?.tank?.capacity?.toInt()?.minus(level?.waterLevel?.toInt()!!)
    Log.d("LEVEL_DATA:","TankDTO en el level: ${level?.tank}")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tanque),
                contentDescription = "Tanque de agua",
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "${level?.fillPercentage?.toInt() ?: "--"}%",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CustomTheme.textOnPrimary,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (5).dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Card(
                modifier = Modifier
                    .size(width = 140.dp, height = 60.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = CustomTheme.cardSecondary)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🟢 Disponible",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomTheme.textOnPrimary
                    )
                    Text(
                        text = "${level?.waterLevel?.toInt() ?: "--"} L",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomTheme.textOnSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

                Card(
                    modifier = Modifier.size(width = 140.dp, height = 60.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = CustomTheme.cardSecondary)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💧 Consumido",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomTheme.textOnPrimary
                        )
                        Text(
                            text = "${consumido ?: "--"} L",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomTheme.textOnSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }











        }

    }

}

