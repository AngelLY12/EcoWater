package com.example.proyecto.ui.screens.Notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyecto.R
import com.example.proyecto.ui.components.layout.ColumnLayout
import com.example.proyecto.ui.components.layout.RowTitle
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.NotificationViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NotificationScreen(navController: NavHostController, notificationViewModel: NotificationViewModel = viewModel(), toastViewModel: ToastViewModel) {
    val isLoading = notificationViewModel.isLoading.value
    val notifications = notificationViewModel.notifications.value
    val context = LocalContext.current




    LaunchedEffect(Unit) {
        notificationViewModel.loadNotifications(context)

    }

    Surface(
        color = CustomTheme.background, // Fondo azul oscuro similar al de la imagen
        modifier = Modifier.fillMaxSize()
    ) {
        ColumnLayout {

            RowTitle(navController=navController, "Notificaciones")

            if(isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }else if(notifications.isEmpty()){
                androidx.compose.material3.Text(
                    text = "Sin notificaciones en la lista",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = CustomTheme.textOnPrimary,
                )

            }else{
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(notifications, key = { _, notification -> notification.idNotification }) { index, notification ->
                        val date = LocalDateTime.parse(notification.createdAt)
                        val formatted = date.format(DateTimeFormatter.ofPattern("MM-dd"))
                        NotificationItem(
                            title = notification.title,
                            date = formatted,
                            message = notification.message,
                            icon = notification.alertType,
                        )

                        NotificationDivider()
                    }
                }

            }
        }
    }
}

@Composable
fun NotificationItem(
    title: String,
    date: String,
    message: String,
    icon: String
) {
    val alertTypeNames = mapOf(
        "LOW_LEVEL" to  R.drawable.nivel,
        "EXCESSIVE_CONSUMPTION" to R.drawable.consumo,
        "FULL_TANK" to R.drawable.deposito_de_agua,
        "HIGH_LEVEL" to R.drawable.nivel,
        "MEDIUM_LEVEL" to R.drawable.nivel
    )
    val iconRes= alertTypeNames[icon]?:R.drawable.ic_notifications
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = CustomTheme.textOnPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomTheme.textOnSecondary,
                    fontWeight = FontWeight.SemiBold                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = CustomTheme.textOnSecondary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun NotificationDivider() {
    Divider(
        color = CustomTheme.textOnPrimary,
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun NotificationScreenPreview(navController: NavHostController, notificationViewModel: NotificationViewModel=viewModel(), toastViewModel: ToastViewModel) {
    NotificationScreen(navController = navController, notificationViewModel = notificationViewModel, toastViewModel = toastViewModel)
}
