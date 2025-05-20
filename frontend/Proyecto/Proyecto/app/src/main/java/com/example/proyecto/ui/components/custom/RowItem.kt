package com.example.proyecto.ui.components.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyecto.model.tank.Tank
import com.example.proyecto.ui.theme.CustomTheme

@Composable
fun <T>  RowItem(onClick: () -> Unit,  item: T,valueText: (T) -> String, title: String, unit: String?=null){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable{onClick()},
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = CustomTheme.textOnPrimary
            )
            Text(
                text = valueText(item) + "${unit}",  // Muestra la capacidad
                style = MaterialTheme.typography.bodyMedium,
                color = CustomTheme.textOnSecondary
            )
        }
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "Ir",
            tint = CustomTheme.textOnPrimary
        )
    }
}