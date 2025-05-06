package com.example.proyecto.ui.screens.Notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AddAlertItem(title: String, description: String, unit: String, threshold: String, alertType: String) {
    val context= LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog){
        FormAddAlert(
            alertType = alertType,
            unit = unit,
            threshold = threshold,
            title = title,
            showForm = showDialog,
            onDismiss = {showDialog=false},
        )

    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B4B75))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall.copy(color = Color.White))
                Text(description, style = MaterialTheme.typography.bodySmall.copy(color = Color.White))
            }
            IconButton(onClick = {
                showDialog=true

            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        }
    }
}

@Composable
fun SectionWithCheckbox(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
    Spacer(modifier = Modifier.height(8.dp))
}
