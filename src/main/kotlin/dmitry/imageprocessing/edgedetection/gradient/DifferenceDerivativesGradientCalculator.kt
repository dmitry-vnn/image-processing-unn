package dmitry.imageprocessing.edgedetection.gradient

import dmitry.imageprocessing.model.PixelColor
import java.awt.image.BufferedImage

class DifferenceDerivativesGradientCalculator(private val image: BufferedImage): GradientCalculator {

    override fun gradientX( x: Int, y: Int): Int {
        return deltaColor(x, y, deltaX = 1)
    }

    override fun gradientY(x: Int, y: Int): Int {
        return deltaColor(x, y, deltaY = 1)
    }

    private fun deltaColor(x: Int, y: Int, deltaX: Int = 0, deltaY: Int = 0): Int {

        image.apply {
            val sourceColor = PixelColor.fromRGB(getRGB(x, y));

            if (x + deltaX !in 0..<width ||
                y + deltaY !in 0..<height) {

                return sourceColor.grayscale

            }

            val incrementColor = PixelColor.fromRGB(getRGB(x + deltaX, y + deltaY))

            return incrementColor.grayscale - sourceColor.grayscale
        }


    }
    
}
