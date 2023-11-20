package dmitry.imageprocessing.compression.transform.haar

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.compression.FrequencyContainer
import dmitry.imageprocessing.model.PixelColor
import java.awt.image.BufferedImage
import kotlin.math.sqrt

class ReverseHaarTransformerImpl(
    private val frequencyContainer: FrequencyContainer
) : ReverseHaarTransformer {

    override fun createImageOnFrequencies(): BufferedImage {
        frequencyContainer.apply {
            val resultImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)

            var point = Point(0, 0)

            for (i in 0..<frequencyContainer.lowerFrequencies.size) {
                val upperFrequency = frequencyContainer.upperFrequencies[i]
                val lowerFrequency = frequencyContainer.lowerFrequencies[i]

                val nextPoint = point.incrementOrNull(imageWidth, imageHeight)

                if (nextPoint == null) {
                    resultImage.setRGB(point.x, point.y, PixelColor.fromBoundedGrayscale(upperFrequency).rgb)
                    break
                }

                val firstPixelIntensity = (1/sqrt(2.0)) * (upperFrequency - lowerFrequency)
                val secondPixelIntensity = (1/sqrt(2.0)) * (upperFrequency + lowerFrequency)

                resultImage.setRGB(point.x, point.y, PixelColor.fromBoundedGrayscale(firstPixelIntensity).rgb)
                resultImage.setRGB(nextPoint.x, nextPoint.y, PixelColor.fromBoundedGrayscale(secondPixelIntensity).rgb)

                point = nextPoint.incrementOrNull(imageWidth, imageWidth) ?: break

            }

            return resultImage
        }
    }

}

private fun Point.incrementOrNull(width: Int, height: Int): Point? {
    val point = Point(x, y)

    point.apply {
        x++
        if (x >= width) {
            x = 0
            y++
        }
        if (y >= height) {
            return null
        }

        return this
    }

}
