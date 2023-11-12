package dmitry.imageprocessing.edgedetection.gradient

import dmitry.imageprocessing.model.PixelColor
import java.awt.image.BufferedImage

class SobelGradientCalculator(private val image: BufferedImage): GradientCalculator {

    private val convolutionKernelX = arrayOf(
        intArrayOf(-1, 0, 1),
        intArrayOf(-2, 0, 2),
        intArrayOf(-1, 0, 1),
    )

    private val convolutionKernelY = arrayOf(
        intArrayOf(1, 2, 1),
        intArrayOf(0, 0, 0),
        intArrayOf(-1, -2, -1),
    )

    override fun gradientX(x: Int, y: Int): Int {
        return calculateIntensity(x, y, convolutionKernelX)
    }

    override fun gradientY(x: Int, y: Int): Int {
        return calculateIntensity(x, y, convolutionKernelY)
    }

    private fun calculateIntensity(x: Int, y: Int, convolutionKernel: Array<IntArray>): Int {

        image.apply {
            val offset = convolutionKernel.size / 2

            val xRange = 0..<width
            val yRange = 0..<height

            if (x - offset !in xRange || x + offset !in xRange || y - offset !in yRange || y + offset !in yRange) {
                return PixelColor.fromRGB(getRGB(x, y)).grayscale;
            }

            var intensity = 0

            for (j in -offset..offset) {
                for (i in -offset..offset) {
                    intensity += PixelColor.fromRGB(getRGB(x + i, y + j)).grayscale *
                            convolutionKernel[offset + j][offset + i]
                }
            }

            return intensity
        }


    }
    
}
