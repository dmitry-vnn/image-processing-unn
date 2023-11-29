package dmitry.imageprocessing.wavelet.apply

import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.wavelet.*
import java.awt.image.BufferedImage

class ReverseHaarTransformRepeater(
    private val image: BufferedImage,
    private val repeatCount: Int
) {

    fun apply(): BufferedImage {
        var count = repeatCount - 1

        val multiMatrix = MatrixOfMultiMatrixOfImage2x2(ImageSplitter(image).split())
        var multiMatrixOnLastStep = multiMatrix

        while (count-- > 0) {
            val nestedMultiMatrix =
                MatrixOfMultiMatrixOfImage2x2(ImageSplitter(multiMatrixOnLastStep[0, 0] as BufferedImage).split())

            multiMatrixOnLastStep[0, 0] = nestedMultiMatrix
            multiMatrixOnLastStep = nestedMultiMatrix
        }

        return applyReverseHaar(multiMatrix)
    }

    private fun applyReverseHaar(multiMatrix: Matrix<Any>): BufferedImage {
        val firstElement = multiMatrix[0, 0]
        val firstElementAsImage = if (firstElement is BufferedImage) firstElement else applyReverseHaar(firstElement as Matrix<Any>)
        return HaarWaveletReverseTransformer(
            ForwardTransformationImageMatrix(
                firstElementAsImage, multiMatrix[0, 1] as BufferedImage,
                multiMatrix[1, 0] as BufferedImage, multiMatrix[1, 1] as BufferedImage
            )
        ).transform()
    }

}

