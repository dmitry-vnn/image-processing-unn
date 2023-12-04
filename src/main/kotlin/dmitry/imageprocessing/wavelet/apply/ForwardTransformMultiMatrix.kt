package dmitry.imageprocessing.wavelet.apply

import dmitry.imageprocessing.matrix.DoubleMatrix
import dmitry.imageprocessing.matrix.MutableMatrix
import dmitry.imageprocessing.wavelet.ForwardTransformMatrix
import java.awt.image.BufferedImage

class ForwardTransformMultiMatrix(
    sourceMatrix: ForwardTransformMatrix
): MutableMatrix<Any> {

    val matrix = Array<Array<Any>>(2) {
        y -> Array(2) {
            x -> sourceMatrix[y, x]
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

    private fun toImageRepresentRecursive(multiMatrix: ForwardTransformMultiMatrix): BufferedImage {
        val firstElement = multiMatrix[0, 0]

        if (firstElement is DoubleMatrix) {
            return ForwardTransformMatrix(
                firstElement,
                multiMatrix[0, 1] as DoubleMatrix,
                multiMatrix[1, 0] as DoubleMatrix,
                multiMatrix[1, 1] as DoubleMatrix,
            ).toImageRepresent()
        }

        return ImageMatrixJoiner(
            toImageRepresentRecursive(firstElement as ForwardTransformMultiMatrix),
            (multiMatrix[0, 1] as DoubleMatrix).toImageRepresent(),
            (multiMatrix[1, 0] as DoubleMatrix).toImageRepresent(),
            (multiMatrix[1, 1] as DoubleMatrix).toImageRepresent()
        ).join()
    }

    fun toImageRepresent() = toImageRepresentRecursive(this)

}