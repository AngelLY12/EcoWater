package com.example.proyecto.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.proyecto.R
import com.example.proyecto.ui.components.custom.CardButton

@Composable
fun StatCards(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CardButton("Consumo", R.drawable.ic_ver_consumo) { /*TODO*/ }
        CardButton("Estadística", R.drawable.ic_estadisticas) { /*TODO*/ }
        CardButton("Reporte", R.drawable.ic_generar_reporte) { /*TODO*/ }
    }
}