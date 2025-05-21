package com.example.proyecto.ui.components.layout

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.proyecto.utils.GlobalEvent
import kotlinx.coroutines.launch

@Composable
fun LogoutHandler(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        GlobalEvent.authEvents.collect { event ->
            if (event is GlobalEvent.AuthEvent.Logout) {
                showDialog = true
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text("Sesión cerrada") },
            text = { Text("Tu sesión ha expirado. Serás redirigido al login.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Sesión expirada. Por favor, inicia sesión de nuevo.")
                    }
                    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    prefs.edit().clear().apply()
                    navController.navigate("login") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }
}
