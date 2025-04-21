package com.example.proyecto.ui.components.custom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.example.proyecto.model.device.Device
import com.example.proyecto.model.tank.Tank
import com.example.proyecto.ui.screens.devices.DeviceItem
import com.example.proyecto.ui.screens.tanks.TankItem

@Composable
fun ExpandableInfoCard(
    name: String,
    imagePainter: Painter,
    capacity: Float? = null,
    isConnected: Boolean? = null,
    device: Device? = null,
    tank: Tank?=null,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
    ) {
        InfoCard(
            name = name,
            imagePainter = imagePainter,
            capacity = capacity,
            isConnected = isConnected,
            onClick = { expanded = !expanded }
        )

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(
                animationSpec = tween(300)
            ) + expandVertically(
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ),
            exit = fadeOut(
                animationSpec = tween(200)
            ) + shrinkVertically(
                animationSpec = tween(200, easing = FastOutSlowInEasing)
            )
        ) {
            if (device != null) {
                DeviceItem(device)
            }
            if(tank!=null){
                TankItem(tank)
            }
        }
    }
}
