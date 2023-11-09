package dmitry.imageprocessing.noise

import org.jfree.chart.ChartFactory
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYItemRenderer
import org.jfree.data.xy.DefaultXYDataset
import java.awt.BasicStroke
import java.awt.Color
import java.awt.image.BufferedImage

class ProbabilityCharts(
    private val probability: DoubleArray,
    private val chartLabel: String
) {

    fun create(): BufferedImage {

        val dataset = DefaultXYDataset()
        val series = Array(2) { index ->
            when (index) {
                0 -> probability.indices.map { it.toDouble() }.toDoubleArray()
                else -> probability
            }
        }

        dataset.addSeries("График функции", series)

        val chart = ChartFactory.createXYLineChart(
            chartLabel,
            "grayscale",
            "f(g)",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            true
        )

        val plot = chart.plot as XYPlot
        val renderer = plot.renderer as XYItemRenderer

        plot.backgroundPaint = Color.WHITE
        plot.domainGridlinePaint = Color.GRAY
        plot.rangeGridlinePaint = Color.GRAY

        renderer.setSeriesPaint(0, Color(0, 98, 175))
        renderer.setSeriesStroke(0, BasicStroke(2.0f))

        return chart.createBufferedImage(800, 600)
    }

}