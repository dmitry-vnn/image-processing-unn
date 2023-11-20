package dmitry.imageprocessing.compression.filters

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.filter.ImageConverter
import dmitry.imageprocessing.model.PixelColor
import java.awt.image.BufferedImage

open class FrequenciesFilter(
    private val imageWidth: Int,
    private val imageHeight: Int,
    private val frequencies: List<Double>,
): ImageConverter {

    override fun convert(): BufferedImage {
        val resultImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)

        val point = Point(0, 0)

        point.apply {

            fun validatePoint(): Boolean {
                if (x >= imageWidth) {
                    x = 0
                    y++
                }

                return y < imageHeight
            }

            for (frequency in frequencies) {
                val color = PixelColor.fromBoundedGrayscale(frequency).rgb

                resultImage.setRGB(x++, y, color)

                if (!validatePoint()) {
                    break
                }

                resultImage.setRGB(x++, y, color)

                if (!validatePoint()) {
                    break
                }
            }

            return resultImage

        }
    }

}