package dmitry.example

import dmitry.example.UQIMetricExample.baseImage
import dmitry.example.UQIMetricExample.comparableImage
import dmitry.metric.ImageUQIMetric
import dmitry.store.ImageLoader

private object UQIMetricExample {
    val baseImage = ImageLoader(PathToExampleEvaluator.evaluateImagePath<UQIMetricExample>("base")).load()
    val comparableImage = ImageLoader(PathToExampleEvaluator.evaluateImagePath<UQIMetricExample>("comparable")).load()
}

fun main() {
    println(ImageUQIMetric(baseImage, comparableImage).calculate())
}