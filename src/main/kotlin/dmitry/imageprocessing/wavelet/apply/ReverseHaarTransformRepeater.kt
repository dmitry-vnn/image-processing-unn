package dmitry.imageprocessing.wavelet.apply

import dmitry.imageprocessing.matrix.DoubleMatrix
import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.wavelet.*
import java.awt.image.BufferedImage

class ReverseHaarTransformRepeater(
    private val multiMatrix: ForwardTransformMultiMatrix,
) {

    fun apply(): BufferedImage {
        return (reverseRecursive(multiMatrix) as DoubleMatrix).toImageRepresent()
    }

    private fun reverseRecursive(multiMatrix: ForwardTransformMultiMatrix): Matrix<Double> {
        val firstElement = multiMatrix[0, 0]

        return HaarWaveletReverseTransformer(
            ForwardTransformMatrix(
                if (firstElement is DoubleMatrix)
                    firstElement
                else
                    reverseRecursive(firstElement as ForwardTransformMultiMatrix),
                multiMatrix[0, 1] as DoubleMatrix,
                multiMatrix[1, 0] as DoubleMatrix,
                multiMatrix[1, 1] as DoubleMatrix,
            )
        ).transform()
    }


    //    var count = repeatCount - 1
    //
    //    val multiMatrix = ForwardTransformMultiMatrix(ImageSplitter(image).split())
    //    var multiMatrixOnLastStep = multiMatrix
    //
    //    while (count-- > 0) {
    //        val nestedMultiMatrix =
    //            ForwardTransformMultiMatrix(ImageSplitter(multiMatrixOnLastStep[0, 0] as BufferedImage).split())
    //
    //        multiMatrixOnLastStep[0, 0] = nestedMultiMatrix
    //        multiMatrixOnLastStep = nestedMultiMatrix
    //    }
    //
    //    return applyReverseHaar(multiMatrix)
    //}
    //
    //private fun applyReverseHaar(multiMatrix: Matrix<Any>): BufferedImage {
    //    val firstElement = multiMatrix[0, 0]
    //    val firstElementAsImage = if (firstElement is BufferedImage) firstElement else applyReverseHaar(firstElement as Matrix<Any>)
    //    return HaarWaveletReverseTransformer(
    //        ForwardTransformMatrix(
    //            firstElementAsImage, multiMatrix[0, 1] as BufferedImage,
    //            multiMatrix[1, 0] as BufferedImage, multiMatrix[1, 1] as BufferedImage
    //        )
    //    ).transform()
    //}

}

