import dmitry.metric.ImageMSEMetric
import dmitry.metric.ImageUQIMetric
import java.awt.image.BufferedImage
import java.io.File
import java.util.Scanner
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    //val imagePath = scanner.nextLine()
    //val comparableImagePath = scanner.nextLine()

    val imagePath = "C:\\Users\\xTopZ\\Pictures\\wallpaper2you_162395.jpg"
    val comparableImagePath = "C:\\Users\\xTopZ\\Pictures\\wallpaper2you_162395.png"

    //calculateMSEMetricAndPrintIt(imagePath, comparableImagePath)
    testUQIMetric(imagePath, comparableImagePath)
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
