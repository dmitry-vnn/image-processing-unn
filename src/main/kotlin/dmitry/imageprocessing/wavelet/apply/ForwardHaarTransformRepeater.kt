package dmitry.imageprocessing.wavelet.apply

import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.wavelet.ForwardTransformationImageMatrix
import dmitry.imageprocessing.wavelet.HaarWaveletForwardTransformer
import dmitry.imageprocessing.wavelet.ImageMatrixJoiner
import java.awt.image.BufferedImage

class ForwardHaarTransformRepeater(
    private val image: BufferedImage,
) {

    fun apply(repeatCount: Int): BufferedImage {
        var count = repeatCount - 1

        val multiMatrix = MatrixOfMultiMatrixOfImage2x2(HaarWaveletForwardTransformer(image).transform())
        var multiMatrixOnLastStep = multiMatrix

        while (count-- > 0) {
            val nestedMultiMatrix =
                MatrixOfMultiMatrixOfImage2x2(HaarWaveletForwardTransformer(multiMatrixOnLastStep[0, 0] as BufferedImage).transform())

            multiMatrixOnLastStep[0, 0] = nestedMultiMatrix
            multiMatrixOnLastStep = nestedMultiMatrix
        }

        return joinMultiMatrixToImage(multiMatrix)
    }

    private fun joinMultiMatrixToImage(multiMatrix: Matrix<Any>): BufferedImage {
        val firstElement = multiMatrix[0, 0]
        val firstElementAsImage = if (firstElement is BufferedImage) firstElement else joinMultiMatrixToImage(firstElement as Matrix<Any>)
        return ImageMatrixJoiner(
            ForwardTransformationImageMatrix(
                firstElementAsImage, multiMatrix[0, 1] as BufferedImage,
                multiMatrix[1, 0] as BufferedImage, multiMatrix[1, 1] as BufferedImage
            )
        ).join()
    }

}

