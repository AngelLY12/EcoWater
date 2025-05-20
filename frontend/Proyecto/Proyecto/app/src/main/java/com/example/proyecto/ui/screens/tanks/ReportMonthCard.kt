package com.example.proyecto.ui.screens.tanks


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.ui.theme.CustomTheme
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ReportMonthCard(
    month: YearMonth,
    onGenerateReport: (YearMonth) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onGenerateReport(month) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Text(
                text = month.format(
                    DateTimeFormatter.ofPattern(
                        "MMMM yyyy",
                        Locale("es", "ES")
                    )
                ).replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium
            )
            Text("Descargar", style = MaterialTheme.typography.bodyMedium, color = CustomTheme.textOnSecondary)
        }
    }
}

