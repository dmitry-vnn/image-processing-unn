package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.matrix.MutableMatrix
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.BufferedImage
import dmitry.imageprocessing.util.ImageExtensions.setPixelColor
import java.awt.image.BufferedImage

class ForwardTransformMatrix(
    private var approximate: Matrix<Double>,
    private var verticalDetailed: Matrix<Double>,
    private var horizontalDetailed: Matrix<Double>,
    private var diagonalDetailed: Matrix<Double>,
): MutableMatrix<Matrix<Double>> {

    override val width get() = 2
    override val height get() = 2

    override operator fun get(y: Int, x: Int): Matrix<Double> {
        return when(x to y) {
            0 to 0 -> approximate
            1 to 0 -> verticalDetailed
            0 to 1 -> horizontalDetailed
            1 to 1 -> diagonalDetailed
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun set(y: Int, x: Int, value: Matrix<Double>) {
        when(x to y) {
            0 to 0 -> approximate = value
            1 to 0 -> verticalDetailed = value
            0 to 1 -> horizontalDetailed = value
            1 to 1 -> diagonalDetailed = value
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun forEachIndexed(action: (x: Int, y: Int, element: Matrix<Double>) -> Unit) {
        action(0, 0, approximate)
        action(1, 0, verticalDetailed)
        action(0, 1, horizontalDetailed)
        action(1, 1, diagonalDetailed)
    }

    fun toImageRepresent(): BufferedImage {

        val quarterWidth = approximate.width;
        val quarterHeight = approximate.height;

        val image = BufferedImage(
            width * quarterWidth,
            height * quarterHeight
        )

        forEachIndexed {i, j, matrix ->

            matrix.forEachIndexed {x, y, floatingIntensity ->

                image.setPixelColor(i * quarterWidth + x, j * quarterHeight + y,
                    PixelColor.fromBoundedGrayscale(floatingIntensity))

            }

        }

        return image
    }

}