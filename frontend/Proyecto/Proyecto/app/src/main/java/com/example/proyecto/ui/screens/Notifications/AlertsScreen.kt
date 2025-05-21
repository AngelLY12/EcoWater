package com.example.proyecto.ui.screens.Notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import androidx.navigation.NavHostController
import com.example.proyecto.R
import com.example.proyecto.ui.components.custom.ExpandableInfoCard
import com.example.proyecto.ui.components.layout.BottomNavigationBar
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.AlertsViewModel
import com.example.proyecto.ui.viewModels.TankViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun AlertsScreen(navController: NavHostController, viewModel: AlertsViewModel = viewModel(), toastViewModel: ToastViewModel) {
    val context= LocalContext.current
    val isLoading = viewModel.isLoading.value
    val alerts = viewModel.alerts.value



    LaunchedEffect(Unit) {
        viewModel.loadAlerts(context)

    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = CustomTheme.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Alertas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CustomTheme.textOnPrimary,
                )
            }

            if(isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }else if(alerts.isEmpty()){
                androidx.compose.material3.Text(
                    text = "Sin alertas en la lista",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = CustomTheme.textOnPrimary,
                )

            }else{
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(alerts, key = { _, alert -> alert.id }) { index, alert ->
                        AlertCard(
                            alert = alert,
                            toastViewModel=toastViewModel
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate("addAlert") },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = "Agregar", tint = CustomTheme.textOnPrimary)
                Spacer(Modifier.width(8.dp))
                Text("Agregar nuevas alertas", color = CustomTheme.textOnPrimary)
            }
        }
    }
}
