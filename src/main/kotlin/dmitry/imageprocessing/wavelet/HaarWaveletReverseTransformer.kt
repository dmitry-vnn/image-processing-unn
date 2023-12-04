package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.matrix.DoubleMatrix
import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.BufferedImage
import dmitry.imageprocessing.util.ImageExtensions.setPixelColor
import kotlin.math.sqrt

class HaarWaveletReverseTransformer(
    private val forwardTransformMatrix: ForwardTransformMatrix
) {

    fun transform(): DoubleMatrix {

        forwardTransformMatrix.apply {
            val rowApproximate = haarReverseColTransform(this[0, 0], this[0, 1])
            val rowDetail = haarReverseColTransform(this[1, 0], this[1, 1])

            return haarReverseRowTransform(rowApproximate, rowDetail)

        }

    }

    private fun haarReverseRowTransform(rowApproximate: Matrix<Double>, rowDetail: Matrix<Double>): DoubleMatrix {
        val matrix = DoubleMatrix(rowApproximate.width, rowApproximate.height * 2)

        for (x in 0..<rowApproximate.width)
            for (y in 0..<rowApproximate.height) {
                val approximateIntensity = rowApproximate[y, x]
                val detailIntensity = rowDetail[y, x]

                val firstIntensity = (approximateIntensity - detailIntensity) / sqrt(2.0)
                val secondIntensity = (approximateIntensity + detailIntensity) / sqrt(2.0)

                matrix[2 * y, x] = firstIntensity
                matrix[2 * y + 1, x] = secondIntensity
            }

        return matrix
    }

    private fun haarReverseColTransform(colApproximate: Matrix<Double>, colDetail: Matrix<Double>): Matrix<Double> {
        val matrix = DoubleMatrix(colApproximate.width * 2, colApproximate.height)

        for (y in 0..<colApproximate.height)
            for (x in 0..<colApproximate.width) {
                val approximateIntensity = colApproximate[y, x]
                val detailIntensity = colDetail[y, x]

                val firstIntensity = (approximateIntensity - detailIntensity) / sqrt(2.0)
                val secondIntensity = (approximateIntensity + detailIntensity) / sqrt(2.0)

                matrix[y, 2 * x] = firstIntensity
                matrix[y, 2 * x + 1] = secondIntensity
            }

        return matrix

    }

}