package com.example.proyecto.ui.screens.Notifications

import androidx.compose.foundation.BorderStroke
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
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.ToastViewModel

@Composable
fun AddAlertItem(title: String, description: String, unit: String, threshold: String, alertType: String, toastViewModel: ToastViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog){
        FormAddAlert(
            alertType = alertType,
            unit = unit,
            threshold = threshold,
            title = title,
            showForm = showDialog,
            onDismiss = {showDialog=false},
            toastViewModel = toastViewModel
        )

    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CustomTheme.cardPrimary),
        border = BorderStroke(1.dp, CustomTheme.cardBorderSecondary)
        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall,color = CustomTheme.textOnPrimary)
                Text(description, style = MaterialTheme.typography.bodyMedium,color = CustomTheme.textOnSecondary)
            }
            IconButton(onClick = {
                showDialog=true

            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = CustomTheme.textOnPrimary)
            }
        }
    }
}

@Composable
fun SectionWithCheckbox(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium,color = CustomTheme.textOnPrimary)
    Spacer(modifier = Modifier.height(8.dp))
}
