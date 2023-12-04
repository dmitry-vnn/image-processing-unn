package dmitry.imageprocessing.matrix

import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.setPixelColor
import java.awt.image.BufferedImage

class DoubleMatrix(
    override val width: Int,
    override val height: Int
): MutableMatrix<Double> {

    val matrix = Array(height) { DoubleArray(width) }

    private val xRange = 0..<width
    private val yRange = 0..<height

    override fun set(y: Int, x: Int, value: Double) {
        validatePoint(x, y)
        matrix[y][x] = value
    }

    private fun validatePoint(x: Int, y: Int) {
        if (x !in xRange) {
            throw IndexOutOfBoundsException("x = $x out of $xRange")
        }

        if (y !in yRange) {
            throw IndexOutOfBoundsException("y = $y out of $yRange")
        }

    }

    override fun get(y: Int, x: Int): Double {
        validatePoint(x, y)
        return matrix[y][x]
    }

    fun toImageRepresent(): BufferedImage {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        forEachIndexed { x, y, floatingIntensity ->
            image.setPixelColor(x, y, PixelColor.fromBoundedGrayscale(floatingIntensity))
        }

        return image

    }

    override fun forEachIndexed(action: (x: Int, y: Int, element: Double) -> Unit) {
        for (y in yRange) {
            for (x in xRange) {
                action(x, y, matrix[y][x])
            }
        }
    }



    override fun toString(): String {
        return StringBuilder().apply {
            for (y in yRange) {
                for (x in xRange) {
                    append(matrix[y][x])
                    if (x < xRange.last) {
                        append(", ")
                    }
                }
                append('\n')
            }
        }.toString()
    }

}