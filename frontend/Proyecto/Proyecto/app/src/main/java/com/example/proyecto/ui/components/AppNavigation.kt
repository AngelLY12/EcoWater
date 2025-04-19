package com.example.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proyecto.ui.components.BottomNavItem
import com.example.proyecto.ui.screens.home.EcoWaterScreenPreview
import com.example.proyecto.ui.screens.devices.DeviceListScreenPreview
import com.example.proyecto.ui.screens.devices.DeviceSetupScreen
import com.example.proyecto.ui.screens.tanks.TankScreenPreview
import com.example.proyecto.ui.screens.devices.DispositivosScreenPreview
import com.example.proyecto.ui.screens.devices.LinkedDeviceScreenPreview
import com.example.proyecto.ui.screens.devices.WifiCredentialsForm
import com.example.proyecto.ui.screens.tanks.FormAddTank
import com.example.proyecto.ui.viewModels.AuthViewModel
import com.example.proyecto.ui.viewModels.BluetoothViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun AppNavigation( navController: NavHostController,
                   authViewModel: AuthViewModel = viewModel() ,
                   toastViewModel: ToastViewModel= viewModel()

) {
    val navController = rememberNavController()
    val context= LocalContext.current
    val tokenState = authViewModel.isLoggedIn.collectAsState().value
    val bluetoothViewModel: BluetoothViewModel = viewModel()

    NavHost(navController = navController,
        startDestination =
        if (tokenState)
         BottomNavItem.Home.route else "login" ) {
        composable("register") { RegisterScreen(navController,toastViewModel) }
        composable("login") { LoginScreen(navController,toastViewModel) }
        composable("dataUser") {DataUser(navController,toastViewModel) }
        composable(BottomNavItem.Home.route) { EcoWaterScreenPreview(navController) }
        composable("addTank") { TankScreenPreview(navController) }
        composable ("¿?"){ DeviceListScreenPreview(navController) }
        composable ("deviceList"){ DispositivosScreenPreview(navController,bluetoothViewModel, onConnected = {
            navController.navigate("setupDevice")
        }, toastViewModel) }
        composable (BottomNavItem.Devices.route){ LinkedDeviceScreenPreview(navController) }
        composable (BottomNavItem.Notifications.route){
            com.example.proyecto.ui.components.DispositivosScreenPreview(
                navController
            )
        }
        composable("formAddTank"){ FormAddTank(navController, toastViewModel) }
        composable("setupDevice") { DeviceSetupScreen(navController,bluetoothViewModel) }
        composable("wifiSetup") { WifiCredentialsForm(navController,bluetoothViewModel, toastViewModel) }

    }
}