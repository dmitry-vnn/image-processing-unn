package dmitry.example

import dmitry.example.CannyOperatorExample.inImagePath
import dmitry.example.CannyOperatorExample.outImagePath
import dmitry.filter.edgedetection.CannyOperator
import dmitry.store.ImageLoader
import dmitry.store.ImageSaver

private object CannyOperatorExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<CannyOperatorExample>("in", "png")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<CannyOperatorExample>("out")
}

fun main() {
    val originalImage = ImageLoader.load(inImagePath)

    val edgeDetectedImage = CannyOperator(originalImage).convert()
    ImageSaver(edgeDetectedImage, outImagePath).save()
}
