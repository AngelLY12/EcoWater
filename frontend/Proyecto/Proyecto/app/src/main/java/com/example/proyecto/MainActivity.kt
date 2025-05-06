package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.ui.components.navigation.AppNavigation
import com.example.proyecto.ui.components.indicators.AppScaffold
import com.example.proyecto.ui.components.custom.CustomToast
import com.example.proyecto.ui.viewModels.AuthViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel
import com.example.proyecto.ui.components.layout.LogoutHandler


class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.let { extras ->
            if (extras.containsKey("from_notification")) {
                val alertType = extras.getString("alert_type")
            }
        }

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val toastViewModel: ToastViewModel = viewModel()
            val context=LocalContext.current

            LaunchedEffect(Unit) {
                authViewModel.updateLoginState(context)
            }

            LogoutHandler(navController, snackbarHostState)



            Box(modifier = Modifier.fillMaxSize()

                ) {
                AppScaffold(navController, snackbarHostState) {
                    AppNavigation(navController, authViewModel)
                }

                CustomToast(
                    message = toastViewModel.toastMessage,
                    visible = toastViewModel.toastVisible,
                    type = toastViewModel.toastType,
                    onDismiss = { toastViewModel.dismissToast() },
                    modifier = Modifier
                        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                )
            }
        }
    }
    override fun onResume() {
        super.onResume()
        authViewModel.updateLoginState(this)
    }
}