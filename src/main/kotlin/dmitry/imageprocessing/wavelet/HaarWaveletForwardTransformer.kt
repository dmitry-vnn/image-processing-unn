package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.matrix.DoubleMatrix
import dmitry.imageprocessing.matrix.Matrix
import kotlin.math.sqrt


class HaarWaveletForwardTransformer(
    val image: Matrix<Double>
) {

    fun transform(): ForwardTransformMatrix {

        val (rowApproximate, rowDetail) = haarRowTransform()

        val (approximate, verticalDetail) = haarColTransform(rowApproximate)
        val (horizontalDetail, diagonalDetail) = haarColTransform(rowDetail)

        return ForwardTransformMatrix(approximate, horizontalDetail, verticalDetail, diagonalDetail)

    }

    private fun haarRowTransform(): Pair<DoubleMatrix, DoubleMatrix> {
        val approximateByRowMatrix = DoubleMatrix(image.width / 2, image.height)
        val detailByRowMatrix = DoubleMatrix(image.width / 2, image.height)

        for (y in 0..<image.height) {
            for (x in 0..<image.width step 2) {
                val firstIntensity = image[y, x]
                val secondIntensity = image[y, x + 1]

                approximateByRowMatrix[y, x / 2] = (firstIntensity + secondIntensity) / sqrt(2.0)
                detailByRowMatrix[y, x / 2] = (secondIntensity - firstIntensity) / sqrt(2.0)
            }
        }

        return approximateByRowMatrix to detailByRowMatrix
    }

    private fun haarColTransform(image: DoubleMatrix): Pair<DoubleMatrix, DoubleMatrix> {
        val approximateByColMatrix = DoubleMatrix(image.width, image.height / 2)
        val detailByColMatrix = DoubleMatrix(image.width, image.height / 2)

        for (x in 0..<image.width) {
            for (y in 0..<image.height step 2) {
                val firstIntensity = image[y, x]
                val secondIntensity = image[y + 1, x]

                approximateByColMatrix[y / 2, x] = (firstIntensity + secondIntensity) / sqrt(2.0)
                detailByColMatrix[y / 2, x] = (secondIntensity - firstIntensity) / sqrt(2.0)
            }
        }

        return approximateByColMatrix to detailByColMatrix
    }

}
