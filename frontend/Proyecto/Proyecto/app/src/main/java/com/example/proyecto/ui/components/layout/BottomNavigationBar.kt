package com.example.proyecto.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyecto.R
import com.example.proyecto.ui.theme.CustomTheme


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Devices,
        BottomNavItem.Notifications,
        BottomNavItem.Profile
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route
    val textCol= CustomTheme.textOnPrimary

    NavigationBar(
        modifier = Modifier.fillMaxWidth().drawBehind{
            drawLine(
                color = textCol,
                start = Offset(0f,0f),
                end = Offset(size.width,0f),
                strokeWidth = 2f
            )
        }
            ,
        containerColor = CustomTheme.background,
    ) {



            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            modifier = Modifier.size(18.dp),
                            contentDescription = item.title,
                            tint = if (currentRoute == item.route) CustomTheme.iconSelected else CustomTheme.iconUnselected
                        )

                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 12.sp
                            ,color=if (currentRoute == item.route) CustomTheme.iconSelected else CustomTheme.iconUnselected
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CustomTheme.textOnPrimary,
                        selectedTextColor = CustomTheme.textOnPrimary,
                        unselectedIconColor = CustomTheme.textOnSecondary,
                        unselectedTextColor = CustomTheme.textOnSecondary,
                        indicatorColor = CustomTheme.background

                    )
                )
            }
        }
}


sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home : BottomNavItem("home", "Inicio", R.drawable.ic_home)
    object Devices : BottomNavItem("devices", "Dispositivos", R.drawable.devices)
    object Notifications : BottomNavItem("notifications", "Alertas", R.drawable.notificaciones)
    object Profile : BottomNavItem("profile", "Perfil", R.drawable.perfil)
}