package dmitry.imageprocessing.edgedetection.gradient

import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.mapToNew
import java.awt.image.BufferedImage

class GradientMagnitude(private val image: BufferedImage, private val calculator: GradientCalculator) {

    fun apply(): BufferedImage {

        return image.mapToNew { x, y ->
            PixelColor.fromBoundedGrayscale(calculator.intensity(x, y))
        }

    }

}