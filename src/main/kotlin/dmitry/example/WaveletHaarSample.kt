package dmitry.example

import dmitry.example.WaveletHaarSample.inImagePath
import dmitry.example.WaveletHaarSample.lowerFrequenciesFilterImagePath
import dmitry.example.WaveletHaarSample.reverseWaveletTransformImagePath
import dmitry.example.WaveletHaarSample.upperFrequenciesFilterImagePath
import dmitry.imageprocessing.compression.filters.LowerFrequenciesFilter
import dmitry.imageprocessing.compression.filters.UpperFrequenciesFilter
import dmitry.imageprocessing.compression.transform.haar.ForwardHaarTransformerImpl
import dmitry.imageprocessing.compression.transform.haar.ReverseHaarTransformerImpl
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver

private object WaveletHaarSample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("in", "png")

    val upperFrequenciesFilterImagePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("upper-freq-filtered", "png")
    val lowerFrequenciesFilterImagePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("lower-freq-filtered", "png")
    val reverseWaveletTransformImagePath = PathToExampleEvaluator.evaluateImagePath<WaveletHaarSample>("reverse-wavelet", "png")
}

fun main() {
    val image = ImageLoader.load(inImagePath)

    val frequencyContainer = ForwardHaarTransformerImpl(image).groupByFrequencies()

    ImageSaver(LowerFrequenciesFilter(frequencyContainer).convert(), lowerFrequenciesFilterImagePath).save()
    ImageSaver(UpperFrequenciesFilter(frequencyContainer).convert(), upperFrequenciesFilterImagePath).save()
    ImageSaver(ReverseHaarTransformerImpl(frequencyContainer).createImageOnFrequencies(), reverseWaveletTransformImagePath).save()
}