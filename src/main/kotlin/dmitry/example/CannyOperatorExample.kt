package dmitry.example

import dmitry.example.CannyOperatorExample.inImagePath
import dmitry.example.CannyOperatorExample.outImagePath
import dmitry.imageprocessing.edgedetection.CannyOperator
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver

private object CannyOperatorExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<CannyOperatorExample>("in", "png")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<CannyOperatorExample>("out", "png")
}

fun main() {
    val originalImage = ImageLoader.load(inImagePath)

    val edgeDetectedImage = CannyOperator(
        originalImage,

        gaussianBlurSigma = 3.0,
        gaussianBlurKernelSize = 5,
        lowerThresholdPercentage = 0.6,
        upperThresholdPercentage = 0.7

    ).detectEdges()

    ImageSaver(edgeDetectedImage, outImagePath).save()
}
