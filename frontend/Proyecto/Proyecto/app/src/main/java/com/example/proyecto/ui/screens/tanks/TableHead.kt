package com.example.proyecto.ui.screens.tanks


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.ui.theme.CustomTheme

@Composable
fun TableHead(modifier: Modifier = Modifier, title: String, subTitle: String) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = CustomTheme.textOnPrimary,
        )



        Text(
            text = subTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = CustomTheme.textOnSecondary,
        )
    }
}