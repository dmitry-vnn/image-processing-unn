package dmitry.example

import dmitry.example.UQIBlockMetricExample.baseImage
import dmitry.example.UQIBlockMetricExample.comparableImage
import dmitry.imageprocessing.metric.ImageUQIBlockMetric
import dmitry.imageprocessing.store.ImageLoader

private object UQIBlockMetricExample {
    val baseImage = ImageLoader.load(PathToExampleEvaluator.evaluateImagePath<UQIBlockMetricExample>("base"))
    val comparableImage = ImageLoader.load(PathToExampleEvaluator.evaluateImagePath<UQIBlockMetricExample>("comparable"))
}

fun main() {
    println(ImageUQIBlockMetric(baseImage, comparableImage, 1000).calculate())
}