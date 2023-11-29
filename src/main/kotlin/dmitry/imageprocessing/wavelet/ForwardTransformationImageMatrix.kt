package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.matrix.MutableMatrix
import java.awt.image.BufferedImage

class ForwardTransformationImageMatrix(
    private var approximate: BufferedImage,
    private var verticalDetailed: BufferedImage,
    private var horizontalDetailed: BufferedImage,
    private var diagonalDetailed: BufferedImage,
): MutableMatrix<BufferedImage> {

    override val width get() = 2
    override val height get() = 2

    override operator fun get(y: Int, x: Int): BufferedImage {
        return when(x to y) {
            0 to 0 -> approximate
            0 to 1 -> verticalDetailed
            1 to 0 -> horizontalDetailed
            1 to 1 -> diagonalDetailed
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun set(y: Int, x: Int, value: BufferedImage) {
        when(x to y) {
            0 to 0 -> approximate = value
            0 to 1 -> verticalDetailed = value
            1 to 0 -> horizontalDetailed = value
            1 to 1 -> diagonalDetailed = value
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun forEachIndexed(action: (x: Int, y: Int, element: BufferedImage) -> Unit) {
        action(0, 0, approximate)
        action(0, 1, verticalDetailed)
        action(1, 0, horizontalDetailed)
        action(1, 1, diagonalDetailed)
    }

}