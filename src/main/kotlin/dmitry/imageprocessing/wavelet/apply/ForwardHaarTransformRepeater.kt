package dmitry.imageprocessing.wavelet.apply

import dmitry.imageprocessing.matrix.DoubleMatrix
import dmitry.imageprocessing.wavelet.HaarWaveletForwardTransformer

class ForwardHaarTransformRepeater(
    private val image: DoubleMatrix,
) {

    fun apply(repeatCount: Int): ForwardTransformMultiMatrix {
        var count = repeatCount - 1

        val sourceMatrix = HaarWaveletForwardTransformer(image).transform()
        val multiMatrix = ForwardTransformMultiMatrix(sourceMatrix)
        var multiMatrixOnLastStep = multiMatrix

        while (count-- > 0) {
            val nestedMultiMatrix =
                ForwardTransformMultiMatrix(HaarWaveletForwardTransformer(multiMatrixOnLastStep[0, 0] as DoubleMatrix).transform())

            multiMatrixOnLastStep[0, 0] = nestedMultiMatrix
            multiMatrixOnLastStep = nestedMultiMatrix
        }

        return multiMatrix
    }


}

