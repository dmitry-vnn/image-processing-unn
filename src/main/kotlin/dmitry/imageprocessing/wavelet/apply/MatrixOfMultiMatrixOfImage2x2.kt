package dmitry.imageprocessing.wavelet.apply

import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.matrix.MutableMatrix
import java.awt.image.BufferedImage

class MatrixOfMultiMatrixOfImage2x2(
    sourceMatrix: Matrix<BufferedImage>
): MutableMatrix<Any> {

    val matrix = Array<Array<Any>>(2) {y -> Array(2) {x ->
            sourceMatrix[y, x]
        }
    }

    override val height get() = 2
    override val width get() = 2

    override fun set(y: Int, x: Int, value: Any) {
        matrix[y][x] = value
    }

    override fun get(y: Int, x: Int): Any {
        return matrix[y][x]
    }

    override fun forEachIndexed(action: (x: Int, y: Int, element: Any) -> Unit) {
        TODO()
    }

}