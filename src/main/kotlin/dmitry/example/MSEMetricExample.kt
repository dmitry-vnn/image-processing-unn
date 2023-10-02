package dmitry.example

import dmitry.example.MSEMetricExample.baseImage
import dmitry.example.MSEMetricExample.comparableImage
import dmitry.metric.ImageMSEMetric
import dmitry.metric.ImageUQIMetric
import dmitry.store.ImageLoader

private object MSEMetricExample {
    val baseImage = ImageLoader(PathToExampleEvaluator.evaluateImagePath<MSEMetricExample>("base")).load()
    val comparableImage = ImageLoader(PathToExampleEvaluator.evaluateImagePath<MSEMetricExample>("comparable")).load()
}

fun main() {
    println(ImageMSEMetric(baseImage, comparableImage).calculate())
}