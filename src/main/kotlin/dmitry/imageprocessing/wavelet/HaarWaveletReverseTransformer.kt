package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.BufferedImage
import dmitry.imageprocessing.util.ImageExtensions.getPixelColor
import dmitry.imageprocessing.util.ImageExtensions.setPixelColor
import java.awt.image.BufferedImage
import kotlin.math.sqrt

class HaarWaveletReverseTransformer(
    private val forwardTransformationImageMatrix: Matrix<BufferedImage>
) {

    fun transform(): BufferedImage {

        forwardTransformationImageMatrix.apply {
            val rowApproximate = haarReverseColTransform(this[0, 0], this[0, 1])
            val rowDetail = haarReverseColTransform(this[1, 0], this[1, 1])

            return haarReverseRowTransform(rowApproximate, rowDetail)

        }

    }

    private fun haarReverseRowTransform(rowApproximate: BufferedImage, rowDetail: BufferedImage): BufferedImage {
        val image = BufferedImage(rowApproximate.width, rowApproximate.height * 2)

        for (x in 0..<rowApproximate.width)
            for (y in 0..<rowApproximate.height) {
                val approximateIntensity = rowApproximate.getPixelColor(x, y).grayscale
                val detailIntensity = rowDetail.getPixelColor(x, y).grayscale

                val firstIntensity = (approximateIntensity - detailIntensity) / sqrt(2.0)
                val secondIntensity = (approximateIntensity + detailIntensity) / sqrt(2.0)

                image.setPixelColor(x, 2 * y, PixelColor.fromBoundedGrayscale(firstIntensity))
                image.setPixelColor(x, 2 * y + 1, PixelColor.fromBoundedGrayscale(secondIntensity))
            }

        return image
    }

    private fun haarReverseColTransform(colApproximate: BufferedImage, colDetail: BufferedImage): BufferedImage {
        val image = BufferedImage(colApproximate.width * 2, colApproximate.height)

        for (y in 0..<colApproximate.height)
            for (x in 0..<colApproximate.width) {
                val approximateIntensity = colApproximate.getPixelColor(x, y).grayscale
                val detailIntensity = colDetail.getPixelColor(x, y).grayscale

                val firstIntensity = (approximateIntensity - detailIntensity) / sqrt(2.0)
                val secondIntensity = (approximateIntensity + detailIntensity) / sqrt(2.0)

                image.setPixelColor(2 * x, y, PixelColor.fromBoundedGrayscale(firstIntensity))
                image.setPixelColor(2 * x + 1, y, PixelColor.fromBoundedGrayscale(secondIntensity))
            }

        return image

    }

}