package dmitry.imageprocessing.edgedetection.threshold

import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilter.Companion.MIDDLE_COLOR
import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilter.Companion.LOWER_COLOR
import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilter.Companion.UPPER_COLOR
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.mapInPlace
import java.awt.image.BufferedImage

class DoubleThresholdFilterImpl(
    private val image: BufferedImage,
    private val lowerBoundPercentage: Double,
    private val upperBoundPercentage: Double
): DoubleThresholdFilter {

    override fun threshold(): BufferedImage {

        image.mapInPlace {x, y ->
            val intensityPercentage = PixelColor.fromRGB(image.getRGB(x, y)).grayscale / 255.0

            PixelColor.fromRGB(when {
                intensityPercentage <= lowerBoundPercentage -> LOWER_COLOR
                intensityPercentage > lowerBoundPercentage && intensityPercentage < upperBoundPercentage -> MIDDLE_COLOR
                else -> UPPER_COLOR
            })
        }

        return image
    }

}