package dmitry.example

import dmitry.example.UQIBlockMetricExample.baseImage
import dmitry.example.UQIBlockMetricExample.comparableImage
import dmitry.metric.ImageUQIBlockMetric
import dmitry.store.ImageLoader

private object UQIBlockMetricExample {
    val baseImage = ImageLoader(PathToExampleEvaluator.evaluateImagePath<UQIBlockMetricExample>("base")).load()
    val comparableImage = ImageLoader(PathToExampleEvaluator.evaluateImagePath<UQIBlockMetricExample>("comparable")).load()
}

fun main() {
    println(ImageUQIBlockMetric(baseImage, comparableImage, 1000).calculate())
}