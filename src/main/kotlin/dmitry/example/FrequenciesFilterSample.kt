package dmitry.example

import dmitry.example.FrequenciesFilterSample.inImagePath
import dmitry.example.FrequenciesFilterSample.lowerFrequenciesFilterImagePath
import dmitry.example.FrequenciesFilterSample.reverseWaveletTransformImagePath
import dmitry.example.FrequenciesFilterSample.upperFrequenciesFilterImagePath
import dmitry.imageprocessing.compression.filters.LowerFrequenciesFilter
import dmitry.imageprocessing.compression.filters.UpperFrequenciesFilter
import dmitry.imageprocessing.compression.transform.haar.ForwardHaarTransformerImpl
import dmitry.imageprocessing.compression.transform.haar.ReverseHaarTransformerImpl
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver

private object FrequenciesFilterSample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<FrequenciesFilterSample>("in", "png")

    val upperFrequenciesFilterImagePath = PathToExampleEvaluator.evaluateImagePath<FrequenciesFilterSample>("upper-freq-filtered", "png")
    val lowerFrequenciesFilterImagePath = PathToExampleEvaluator.evaluateImagePath<FrequenciesFilterSample>("lower-freq-filtered", "png")
    val reverseWaveletTransformImagePath = PathToExampleEvaluator.evaluateImagePath<FrequenciesFilterSample>("reverse-wavelet", "png")
}

fun main() {
    val image = ImageLoader.load(inImagePath)

    val frequencyContainer = ForwardHaarTransformerImpl(image).groupByFrequencies()

    ImageSaver(LowerFrequenciesFilter(frequencyContainer).convert(), lowerFrequenciesFilterImagePath).save()
    ImageSaver(UpperFrequenciesFilter(frequencyContainer).convert(), upperFrequenciesFilterImagePath).save()
    ImageSaver(ReverseHaarTransformerImpl(frequencyContainer).createImageOnFrequencies(), reverseWaveletTransformImagePath).save()
}