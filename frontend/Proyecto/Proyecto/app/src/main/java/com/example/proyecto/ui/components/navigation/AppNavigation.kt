package com.example.proyecto.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.login.DataUser
import com.example.login.LoginScreen
import com.example.login.RegisterScreen
import com.example.proyecto.ui.components.layout.BottomNavItem
import com.example.proyecto.ui.screens.Notifications.AddAlertsScreen
import com.example.proyecto.ui.screens.Notifications.AlertsScreen
import com.example.proyecto.ui.screens.Notifications.NotificationScreenPreview
import com.example.proyecto.ui.screens.home.EcoWaterScreenPreview
import com.example.proyecto.ui.screens.devices.DeviceListScreenPreview
import com.example.proyecto.ui.screens.devices.DeviceSetupScreen
import com.example.proyecto.ui.screens.tanks.TankScreenPreview
import com.example.proyecto.ui.screens.devices.DispositivosScreenPreview
import com.example.proyecto.ui.screens.devices.LinkedDeviceScreenPreview
import com.example.proyecto.ui.screens.devices.WifiCredentialsForm
import com.example.proyecto.ui.screens.profile.ProfileScreenPreview
import com.example.proyecto.ui.screens.tanks.ConsumeTankScreenPreview
import com.example.proyecto.ui.screens.tanks.FormAddTank
import com.example.proyecto.ui.screens.tanks.ReportScreenScreenPreview
import com.example.proyecto.ui.screens.tanks.StatisticalTankScreenPreview
import com.example.proyecto.ui.viewModels.AuthViewModel
import com.example.proyecto.ui.viewModels.BluetoothViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun AppNavigation( navController: NavHostController,
                   authViewModel: AuthViewModel = viewModel() ,
                   toastViewModel: ToastViewModel= viewModel()

) {
    val tokenState = authViewModel.isLoggedIn.collectAsState().value
    val bluetoothViewModel: BluetoothViewModel = viewModel()

    LaunchedEffect(tokenState) {
        if (!tokenState) {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController,
        startDestination =
        if (tokenState)
         BottomNavItem.Home.route else "login" ) {
        composable("register") { RegisterScreen(navController, toastViewModel) }
        composable("login") { LoginScreen(navController, toastViewModel, authViewModel) }
        composable("dataUser") { DataUser(navController, toastViewModel) }
        composable("addTank") { TankScreenPreview(navController, toastViewModel) }
        composable("consumeScreen") { ConsumeTankScreenPreview(navController) }
        composable("statisticalScreen") { StatisticalTankScreenPreview(navController) }
        composable("reportScreen") { ReportScreenScreenPreview(navController) }
        composable ("¿?"){ DeviceListScreenPreview(navController, toastViewModel) }
        composable ("deviceList"){ DispositivosScreenPreview(navController,bluetoothViewModel, onConnected = {
            navController.navigate("setupDevice")
        }, toastViewModel) }
        composable ("notfTank"){
            NotificationScreenPreview (
                navController, toastViewModel = toastViewModel
            )
        }
        composable("formAddTank"){ FormAddTank(navController, toastViewModel) }
        composable("setupDevice") { DeviceSetupScreen(navController,bluetoothViewModel, toastViewModel) }
        composable("wifiSetup") { WifiCredentialsForm(navController,bluetoothViewModel, toastViewModel) }
        composable(BottomNavItem.Home.route) { EcoWaterScreenPreview(navController, toastViewModel) }
        composable (BottomNavItem.Notifications.route){ AlertsScreen(navController, toastViewModel = toastViewModel) }
        composable (BottomNavItem.Devices.route){ LinkedDeviceScreenPreview(navController, toastViewModel) }
        composable (BottomNavItem.Profile.route){ ProfileScreenPreview(navController, authViewModel, toastViewModel) }
        composable ("addAlert"){ AddAlertsScreen(navController, toastViewModel) }

    }
}