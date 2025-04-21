package com.example.proyecto.ui.screens.home

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
import androidx.core.content.ContextCompat
import com.example.proyecto.R
import com.example.proyecto.model.level.Levels
import com.example.proyecto.ui.theme.chartColor
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.min

@Composable
fun DailyChart(levels: List<Levels>) {
    val maxCapacity = levels.maxOfOrNull { it.tank?.capacity ?: 0f } ?: 1000f
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = chartColor)
    ) {
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setBackgroundColor(chartColor.toArgb())
                    description.isEnabled = false
                    legend.isEnabled = false
                    setTouchEnabled(true)
                    setPinchZoom(true)
                    extraBottomOffset = 15f
                    extraLeftOffset = 10f
                    extraRightOffset = 10f

                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

                    val entries = levels.mapIndexed { index, level ->
                        Entry(
                            index.toFloat(),
                            level.waterLevel ?: 0f
                        ).apply {
                            data = level.dateMeasurement?.let { dateFormat.format(it) } ?: ""
                        }
                    }

                    // 3. Dataset con degradado
                    val dataSet = LineDataSet(entries, "").apply {
                        color = Color.White.toArgb()
                        lineWidth = 3f
                        setDrawCircles(true)
                        circleRadius = 5f
                        setCircleColor(Color.White.toArgb())
                        setDrawValues(false)
                        mode = LineDataSet.Mode.LINEAR
                        fillDrawable = ContextCompat.getDrawable(context, R.drawable.chart_gradient)
                        setDrawFilled(true)
                        setDrawHorizontalHighlightIndicator(false)
                    }

                    // 4. Eje X optimizado
                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        setLabelCount(min(5, levels.size), true) // Máximo 5 etiquetas
                        valueFormatter = object : ValueFormatter() {
                            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                                return (entries.getOrNull(value.toInt())?.data as? String) ?: ""
                            }
                        }
                        textSize = 10f
                        textColor = Color.White.toArgb()
                        yOffset = 5f // Separación del borde
                    }

                    // 5. Eje Y mejorado
                    setAutoScaleMinMaxEnabled(false)
                    axisLeft.apply {
                        axisMinimum = 0f
                        axisMaximum = (maxCapacity+100)
                        granularity = if (maxCapacity > 0) 100f else 1f
                        setGranularityEnabled(true)
                        gridColor = Color.White.toArgb()
                        textColor = Color.White.toArgb()
                        setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
                    }

                    axisRight.isEnabled = false

                    // 6. Configuración final
                    data = LineData(dataSet)
                    animateY(500, Easing.EaseInOutCubic)
                    setVisibleXRangeMaximum(6f)
                    moveViewToX(entries.lastIndex.toFloat())
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
