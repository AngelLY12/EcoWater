package com.example.proyecto.ui.screens.tanks


import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.proyecto.model.tankFilling.TankFilling
import com.example.proyecto.ui.theme.CustomTheme
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TankFillingBarChart(fillings: List<TankFilling>) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth().padding(6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = CustomTheme.cardPrimary)
    ) {
        val cardColor = CustomTheme.cardSecondary
        val text = CustomTheme.textOnPrimary

        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setBackgroundColor(cardColor.toArgb())
                    description.isEnabled = false
                    legend.isEnabled = false
                    setTouchEnabled(true)
                    setPinchZoom(true)

                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

                    val entries = fillings.mapIndexed { index, filling ->
                        BarEntry(
                            index.toFloat(),
                            filling.totalVolume ?: 0f
                        ).apply {
                            data = filling.finishedDate?.let {
                                dateFormat.format(Date(it.time)) // Convertir Timestamp a Date antes de formatear
                            } ?: ""
                        }
                    }

                    val dataSet = BarDataSet(entries, "Volumen Total").apply {
                        color = text.toArgb()
                        setDrawValues(true)
                        valueTextSize = 10f
                        valueTextColor = text.toArgb()
                    }

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        valueFormatter = object : ValueFormatter() {
                            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                                return (entries.getOrNull(value.toInt())?.data as? String) ?: ""
                            }
                        }
                        textSize = 10f
                        textColor =text.toArgb()
                    }

                    axisLeft.apply {
                        axisMinimum = 0f
                        axisMaximum = (fillings.maxOfOrNull { it.totalVolume ?: 0f } ?: 1000f) + 100
                        granularity = 100f
                        textColor = text.toArgb()
                    }

                    axisRight.isEnabled = false

                    data = BarData(dataSet)
                    animateY(500, Easing.EaseInOutCubic)
                    invalidate()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(300.dp)
        )
    }
}