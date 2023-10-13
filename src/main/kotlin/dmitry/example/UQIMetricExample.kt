package dmitry.example

import dmitry.example.UQIMetricExample.baseImage
import dmitry.example.UQIMetricExample.comparableImage
import dmitry.metric.ImageUQIMetric
import dmitry.store.ImageLoader

private object UQIMetricExample {
    val baseImage = ImageLoader.load(PathToExampleEvaluator.evaluateImagePath<UQIMetricExample>("base"))
    val comparableImage = ImageLoader.load(PathToExampleEvaluator.evaluateImagePath<UQIMetricExample>("comparable"))
}

fun main() {
    println(ImageUQIMetric(baseImage, comparableImage).calculate())
}