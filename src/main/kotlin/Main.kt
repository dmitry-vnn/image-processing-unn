import dmitry.metric.ImageMSEMetric
import dmitry.metric.ImageUQIMetric
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
}

fun testUQIMetric(imagePath: String, comparableImagePath: String) {
    println(
        ImageUQIMetric(imagePath.toImage(), comparableImagePath.toImage()).calculate()
    )
}

fun calculateMSEMetricAndPrintIt(imagePath: String, comparableImagePath: String) {
    val metricResult = ImageMSEMetric(
        imagePath.toImage(),
        comparableImagePath.toImage()
    ).calculate()

    println(metricResult)
}

private fun String.toImage(): BufferedImage {
    return ImageIO.read(File(this))
}
