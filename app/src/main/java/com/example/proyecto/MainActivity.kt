package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.AppNavigation
import com.example.login.LoginScreen
import com.example.login.RegisterScreen
import com.example.proyecto.ui.components.AppScaffold
import com.example.proyecto.ui.components.CustomToast
import com.example.proyecto.ui.viewModels.AuthViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel
import com.example.proyecto.utils.GlobalEvent
import com.example.proyecto.utils.LogoutHandler
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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