package dmitry.example

import dmitry.example.WaveletHaarSample.forwardOncePath
import dmitry.example.WaveletHaarSample.forwardThricePath
import dmitry.example.WaveletHaarSample.forwardTwicePath
import dmitry.example.WaveletHaarSample.inImagePath
import dmitry.example.WaveletHaarSample.reverseOncePath
import dmitry.example.WaveletHaarSample.reverseOnceZeroDiagonalPath
import dmitry.example.WaveletHaarSample.reverseOnceZeroHorizontalAndVerticalPath
import dmitry.example.WaveletHaarSample.reverseThricePath
import dmitry.example.WaveletHaarSample.reverseTwicePath
import dmitry.imageprocessing.filter.point.MonochromeGrayConverter
import dmitry.imageprocessing.matrix.DoubleMatrix
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver
import dmitry.imageprocessing.util.ImageExtensions.forEachIndexed
import dmitry.imageprocessing.wavelet.ForwardTransformMatrix
import dmitry.imageprocessing.wavelet.HaarWaveletForwardTransformer
import dmitry.imageprocessing.wavelet.HaarWaveletReverseTransformer
import dmitry.imageprocessing.wavelet.apply.ForwardHaarTransformRepeater
import dmitry.imageprocessing.wavelet.apply.ReverseHaarTransformRepeater
import java.awt.image.BufferedImage

private object WaveletHaarSample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("in", "png")

    val forwardOncePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("forward-once", "png")
    val forwardTwicePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("forward-twice", "png")
    val forwardThricePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("forward-thrice", "png")

    val reverseOncePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("reverse-once", "png")
    val reverseTwicePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("reverse-twice", "png")
    val reverseThricePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("reverse-thrice", "png")

    val reverseOnceZeroDiagonalPath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("rev-one-zero-diag", "png")
    val reverseOnceZeroHorizontalAndVerticalPath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("rev-one-zero-vert-horiz", "png")
}

fun main() {
    val image = ImageLoader.load(inImagePath)

    val forwardRepeater = ForwardHaarTransformRepeater(image.toDoubleMatrix())

    val forwardOnceMatrix = forwardRepeater.apply(1)
    val forwardTwiceMatrix = forwardRepeater.apply(2)
    val forwardThriceMatrix = forwardRepeater.apply(3)

    val reverseOnce = ReverseHaarTransformRepeater(forwardOnceMatrix).apply()
    val reverseTwice = ReverseHaarTransformRepeater(forwardTwiceMatrix).apply()
    val reverseThrice = ReverseHaarTransformRepeater(forwardThriceMatrix).apply()

    ImageSaver(forwardOnceMatrix.toImageRepresent(), forwardOncePath).save()
    ImageSaver(forwardTwiceMatrix.toImageRepresent(), forwardTwicePath).save()
    ImageSaver(forwardThriceMatrix.toImageRepresent(), forwardThricePath).save()

    ImageSaver(reverseOnce, reverseOncePath).save()
    ImageSaver(reverseTwice, reverseTwicePath).save()
    ImageSaver(reverseThrice, reverseThricePath).save()


    val forwardOneWithZeroDiagonal = forwardRepeater.apply(1)
    forwardOneWithZeroDiagonal.apply {
        val (width, height) = (this[0, 0] as DoubleMatrix).run { width to height }
        this[1, 1] = DoubleMatrix(width, height)
    }

    val forwardOneWithZeroVerticalAndHorizontal = forwardRepeater.apply(1)
    forwardOneWithZeroVerticalAndHorizontal.apply {
        val (width, height) = (this[0, 0] as DoubleMatrix).run { width to height }
        this[1, 0] = DoubleMatrix(width, height)
        this[0, 1] = DoubleMatrix(width, height)
        this[1, 1] = DoubleMatrix(width, height)
    }

    ImageSaver(ReverseHaarTransformRepeater(forwardOneWithZeroDiagonal).apply(), reverseOnceZeroDiagonalPath).save()
    ImageSaver(ReverseHaarTransformRepeater(forwardOneWithZeroVerticalAndHorizontal).apply(), reverseOnceZeroHorizontalAndVerticalPath).save()
}

private fun BufferedImage.toDoubleMatrix(): DoubleMatrix {
    val matrix = DoubleMatrix(width, height)

    forEachIndexed {x, y, color ->
        matrix[y, x] = color.grayscale.toDouble()
    }

    return matrix
}
