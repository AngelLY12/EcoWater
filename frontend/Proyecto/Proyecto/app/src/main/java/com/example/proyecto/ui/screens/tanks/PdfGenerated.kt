package com.example.proyecto.ui.screens.tanks


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.graphics.Color
import android.graphics.Typeface
import android.os.Environment
import com.example.proyecto.model.level.Levels
import com.example.proyecto.model.tankFilling.TankFilling
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach
import java.time.format.DateTimeFormatter

fun createChartImage(context: Context, levels: List<Levels>): Bitmap {
    val entries = levels.mapIndexed { index, level ->
        Entry(index.toFloat(), level.waterLevel ?: 0f).apply {
            data = level.dateMeasurement?.let {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(it.time))
            } ?: ""
        }
    }

    val chart = LineChart(context).apply {
        data = LineData(LineDataSet(entries, "Niveles de Agua").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawCircles(true)
            setDrawValues(true)
        })
        layoutParams = ViewGroup.LayoutParams(800, 600)
        measure(
            View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY)
        )
        layout(0, 0, 800, 600)
    }

    return chart.chartBitmap
}

fun createLineChartImage(context: Context, levels: List<Levels>): Bitmap {
    val entries = levels.mapIndexed { index, level ->
        Entry(index.toFloat(), level.waterLevel ?: 0f).apply {
            data = level.dateMeasurement?.let {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(it.time))
            } ?: ""
        }
    }

    val lineChart = LineChart(context).apply {
        data = LineData(LineDataSet(entries, "Niveles de Agua").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawCircles(true)
            setDrawValues(true)
        })
        layoutParams = ViewGroup.LayoutParams(800, 600)
        measure(
            View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY)
        )
        layout(0, 0, 800, 600)
    }

    return lineChart.chartBitmap
}

fun createBarChartImage(context: Context, levels: List<TankFilling>): Bitmap {
    val entries = levels.mapIndexed { index, level ->
        BarEntry(index.toFloat(), level.totalVolume ?: 0f).apply {
            data = level.finishedDate?.let {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(it.time))
            } ?: ""
        }
    }

    val barChart = BarChart(context).apply {
        data = BarData(BarDataSet(entries, "Niveles de Agua").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawValues(true)
        })
        layoutParams = ViewGroup.LayoutParams(800, 600)
        measure(
            View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY)
        )
        layout(0, 0, 800, 600)
    }

    return barChart.chartBitmap
}

fun generatePdf(context: Context, groupedByDate: Map<LocalDate, List<Levels>>): PdfDocument {
    val document = PdfDocument()
    val paint = Paint().apply {
        textSize = 30f
        color = Color.BLACK
    }

    var pageNumber = 1
    groupedByDate.forEach { (date, levels) ->
        val pageInfo = PdfDocument.PageInfo.Builder(800, 1000, pageNumber).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        // Título con fecha
        canvas.drawText("Reporte del Día: $date", 100f, 50f, paint)

        // Insertar datos
        var yPos = 100f
        levels.forEach { level ->
            canvas.drawText(
                "Hora: ${
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(Date(level.dateMeasurement?.time ?: 0))
                }, Nivel: ${level.waterLevel}",
                120f, yPos, paint
            )
            yPos += 40
        }

        // 🔥 **Crear y agregar la gráfica** 🔥
        val bitmap = createChartImage(context, levels) // Generar gráfica de ese día
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 600, 400, false)
        canvas.drawBitmap(scaledBitmap, 100f, yPos + 50f, paint)

        document.finishPage(page)
        pageNumber++
    }

    return document // Retorna el PDF con todas las gráficas
}

fun generatePdfWithBothCharts(
    context: Context,
    groupedByDate: Map<LocalDate, List<Levels>>,
    otherGroupedData: Map<LocalDate, List<TankFilling>>
): PdfDocument {
    val document = PdfDocument()

    // 🔹 Paint para título principal
    val titlePage = Paint().apply {
        textSize = 38f
        color = Color.RED
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    val titlePaint = Paint().apply {
        textSize = 32f
        color = Color.BLACK
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    // 🔹 Paint para subtítulos
    val subtitlePaint = Paint().apply {
        textSize = 24f
        color = Color.DKGRAY
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    }

    // 🔹 Paint para texto de contenido
    val contentPaint = Paint().apply {
        textSize = 18f
        color = Color.BLACK
    }

    var pageNumber = 1
    groupedByDate.forEach { (date, levels) ->
        val pageInfo = PdfDocument.PageInfo.Builder(800, 1200, pageNumber).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val pageWidth = pageInfo.pageWidth

        val formatter =
            DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val formattedDate = date.format(formatter).replaceFirstChar { it.uppercase() }

        val title = formattedDate
        val titleX = (pageWidth - titlePaint.measureText(title)) / 2
        canvas.drawText(title, titleX, 50f, titlePage)

        // 🔹 Datos principales
        var yPos = 80f
        levels.forEach { level ->
            yPos += 2f
        }

        // 🔥 Gráfico de líneas
        if (levels.isNotEmpty()) {
            val totalWaterLevel = levels.mapNotNull { it.waterLevel }.sum()
            val formattedTotal = String.format("%.2f", totalWaterLevel)

            yPos += 30f
            canvas.drawText("Consumido (litros/hora)", 100f, yPos, titlePaint)
            yPos += 30f
            canvas.drawText("$formattedTotal listros", 100f, yPos, subtitlePaint)
            yPos += 30f

            val lineBitmap = createLineChartImage(context, levels)
            val scaledLineBitmap = Bitmap.createScaledBitmap(lineBitmap, 600, 400, false)
            canvas.drawBitmap(scaledLineBitmap, 100f, yPos, contentPaint)
            yPos += 450f
        }

        // 🔥 Gráfico de barras
        val tankFillings = otherGroupedData[date]
        if (!tankFillings.isNullOrEmpty()) {
            val totalHours = when (tankFillings.size) {
                0 -> 0L // Sin datos
                1 -> {
                    val single = tankFillings.first()
                    val start = single.startedDate?.time
                    val end = single.finishedDate?.time
                    if (start != null && end != null) {
                        java.time.Duration.between(
                            Instant.ofEpochMilli(start).atZone(ZoneId.systemDefault())
                                .toLocalDateTime(),
                            Instant.ofEpochMilli(end).atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                        ).toHours()
                    } else 0L
                }

                else -> {
                    tankFillings.mapNotNull { it.finishedDate?.time }
                        .map {
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                        }
                        .sorted()
                        .zipWithNext { prev, next ->
                            java.time.Duration.between(prev, next).toHours()
                        }
                        .sum()
                }
            }

            canvas.drawText("Llenado (litros/hora)", 100f, yPos, titlePaint)
            yPos += 30f
            canvas.drawText("$totalHours horas", 100f, yPos, subtitlePaint)
            yPos += 30f

            val barBitmap = createBarChartImage(context, tankFillings)
            val scaledBarBitmap = Bitmap.createScaledBitmap(barBitmap, 600, 400, false)
            canvas.drawBitmap(scaledBarBitmap, 100f, yPos, contentPaint)
        }

        document.finishPage(page)
        pageNumber++
    }

    return document
}


fun savePdfToDownloads(context: Context, document: PdfDocument, fileName: String) {
    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, fileName)

    try {
        file.outputStream().use { document.writeTo(it) }
        document.close()
        Toast.makeText(
            context,
            "PDF guardado en Descargas: ${file.absolutePath}",
            Toast.LENGTH_LONG
        ).show()
    } catch (e: IOException) {
        Toast.makeText(context, "Error al guardar el PDF: ${e.message}", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }
}
