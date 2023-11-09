package dmitry.example

import dmitry.example.CannyOperatorExample.inImagePath
import dmitry.example.CannyOperatorExample.outImagePath
import dmitry.imageprocessing.filter.edgedetection.CannyOperator
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver

private object CannyOperatorExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<CannyOperatorExample>("in", "png")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<CannyOperatorExample>("out")
}

fun main() {
    val originalImage = ImageLoader.load(inImagePath)

    val edgeDetectedImage = CannyOperator(originalImage).convert()
    ImageSaver(edgeDetectedImage, outImagePath).save()
}
