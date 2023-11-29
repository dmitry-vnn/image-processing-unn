package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.BufferedImage
import dmitry.imageprocessing.util.ImageExtensions.getPixelColor
import java.awt.image.BufferedImage
import kotlin.math.sqrt

class HaarWaveletForwardTransformer(
    val image: BufferedImage
) {

    fun transform(): ForwardTransformationImageMatrix {

        val (rowApproximate, rowDetail) = haarRowTransform(image)

        val (approximate, verticalDetail) = haarColTransform(rowApproximate)
        val (horizontalDetail, diagonalDetail) = haarColTransform(rowDetail)

        return ForwardTransformationImageMatrix(approximate, verticalDetail, horizontalDetail, diagonalDetail)

    }

    private fun haarRowTransform(image: BufferedImage): Pair<BufferedImage, BufferedImage> {
        val approximateByRowImage = BufferedImage(image.width / 2, image.height)
        val detailByRowImage = BufferedImage(image.width / 2, image.height)

        for (y in 0..<image.height) {

            for (x in 0..<image.width step 2) {
                val firstIntensity = image.getPixelColor(x, y).grayscale
                val secondIntensity = image.getPixelColor(x + 1, y).grayscale

                approximateByRowImage.setRGB(x / 2, y, PixelColor.fromBoundedGrayscale(
                    (firstIntensity + secondIntensity) / sqrt(2.0)
                ).rgb)

                detailByRowImage.setRGB(x / 2, y, PixelColor.fromBoundedGrayscale(
                    (secondIntensity - firstIntensity) / sqrt(2.0)
                ).rgb)

            }

        }

        return approximateByRowImage to detailByRowImage
    }

    private fun haarColTransform(image: BufferedImage): Pair<BufferedImage, BufferedImage> {
        val approximateByColImage = BufferedImage(image.width, image.height / 2)
        val detailByColImage = BufferedImage(image.width, image.height / 2)

        for (x in 0..<image.width) {

            for (y in 0..<image.height step 2) {
                val firstIntensity = image.getPixelColor(x, y).grayscale
                val secondIntensity = image.getPixelColor(x, y + 1).grayscale

                approximateByColImage.setRGB(x, y / 2, PixelColor.fromBoundedGrayscale(
                    (firstIntensity + secondIntensity) / sqrt(2.0)
                ).rgb)

                detailByColImage.setRGB(x, y / 2, PixelColor.fromBoundedGrayscale(
                    (secondIntensity - firstIntensity) / sqrt(2.0)
                ).rgb)

            }

        }

        return approximateByColImage to detailByColImage
    }

}