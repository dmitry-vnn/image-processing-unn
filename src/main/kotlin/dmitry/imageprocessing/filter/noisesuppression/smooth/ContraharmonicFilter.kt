package dmitry.imageprocessing.filter.noisesuppression.smooth

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.filter.ImageConverter
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.copy
import dmitry.imageprocessing.util.ImageExtensions.getPixelColor
import dmitry.imageprocessing.util.ImageExtensions.setPixelColor
import java.awt.image.BufferedImage
import kotlin.math.pow

class ContraharmonicFilter(
    private val image: BufferedImage,
    private val neighbourhoodRadius: Int,
    private val q: Double
): ImageConverter {

    override fun convert(): BufferedImage {

        val result = image.copy()

        image.apply {
            val radius = neighbourhoodRadius
            for (y in radius..<height - radius)
                for (x in radius..<width - radius) {

                    val neighbourhood = -radius..radius
                    val color = image.applyContraharmonicInArea(Point(x, y), neighbourhood)

                    result.setPixelColor(x, y, color)

                }
        }

        return result

    }


    private fun BufferedImage.applyContraharmonicInArea(center: Point, area: IntRange): PixelColor {
        var redNumerator = 0.0
        var greenNumerator = 0.0
        var blueNumerator = 0.0

        var redDenominator = 0.0
        var greenDenominator = 0.0
        var blueDenominator = 0.0

        for (j in area)
            for (i in area) {
                val pixelColor = getPixelColor(center.x + i, center.y + j)

                redNumerator += pixelColor.red pow (q + 1)
                greenNumerator += pixelColor.green pow (q + 1)
                blueNumerator += pixelColor.blue pow (q + 1)

                redDenominator += pixelColor.red pow q
                greenDenominator += pixelColor.green pow q
                blueDenominator += pixelColor.blue pow q

            }

        return PixelColor.fromBoundedRGB(
            redNumerator / redDenominator,
            greenNumerator / greenDenominator,
            blueNumerator / blueDenominator
        )
    }
}

private infix fun Number.pow(power: Double): Double {
    return toDouble().pow(power)
}
