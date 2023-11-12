package dmitry.imageprocessing.edgedetection.threshold

import dmitry.imageprocessing.model.PixelColor
import java.awt.image.BufferedImage

interface DoubleThresholdFilter {

    companion object {

        private val Int.asRgb: Int
            get() = PixelColor.fromGrayscale(this).rgb

        val UPPER_COLOR = 255.asRgb
        val MIDDLE_COLOR = 127.asRgb
        val LOWER_COLOR = 0.asRgb
    }


    fun threshold(lowerBoundPercentage: Double, upperBoundPercentage: Double): BufferedImage
}
