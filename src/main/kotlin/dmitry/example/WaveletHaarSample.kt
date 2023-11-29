package dmitry.example

import dmitry.example.WaveletHaarSample.forwardOncePath
import dmitry.example.WaveletHaarSample.forwardThricePath
import dmitry.example.WaveletHaarSample.forwardTwicePath
import dmitry.example.WaveletHaarSample.inImagePath
import dmitry.example.WaveletHaarSample.reverseOncePath
import dmitry.example.WaveletHaarSample.reverseThricePath
import dmitry.example.WaveletHaarSample.reverseTwicePath
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver
import dmitry.imageprocessing.wavelet.apply.ForwardHaarTransformRepeater
import dmitry.imageprocessing.wavelet.apply.ReverseHaarTransformRepeater

private object WaveletHaarSample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("in", "png")

    val forwardOncePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("forward-once", "png")
    val forwardTwicePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("forward-twice", "png")
    val forwardThricePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("forward-thrice", "png")

    val reverseOncePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("reverse-once", "png")
    val reverseTwicePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("reverse-twice", "png")
    val reverseThricePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("reverse-thrice", "png")
}

fun main() {
    val image = ImageLoader.load(inImagePath)


    val forwardRepeater = ForwardHaarTransformRepeater(image)

    val forwardOnce = forwardRepeater.apply(1)
    val forwardTwice = forwardRepeater.apply(2)
    val forwardThrice = forwardRepeater.apply(3)

    val reverseOnce = ReverseHaarTransformRepeater(forwardOnce, 1).apply()
    val reverseTwice = ReverseHaarTransformRepeater(forwardTwice, 2).apply()
    val reverseThrice = ReverseHaarTransformRepeater(forwardThrice, 3).apply()

    ImageSaver(forwardOnce, forwardOncePath).save()
    ImageSaver(forwardTwice, forwardTwicePath).save()
    ImageSaver(forwardThrice, forwardThricePath).save()

    ImageSaver(reverseOnce, reverseOncePath).save()
    ImageSaver(reverseTwice, reverseTwicePath).save()
    ImageSaver(reverseThrice, reverseThricePath).save()
}
