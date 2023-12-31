package dmitry.example

import dmitry.example.MSEMetricExample.baseImage
import dmitry.example.MSEMetricExample.comparableImage
import dmitry.imageprocessing.metric.ImageMSEMetric
import dmitry.imageprocessing.store.ImageLoader

private object MSEMetricExample {
    val baseImage = ImageLoader.load(PathToExampleEvaluator.evaluateImagePath<MSEMetricExample>("base"))
    val comparableImage = ImageLoader.load(PathToExampleEvaluator.evaluateImagePath<MSEMetricExample>("comparable"))
}

fun main() {
    println(ImageMSEMetric(baseImage, comparableImage).calculate())
}