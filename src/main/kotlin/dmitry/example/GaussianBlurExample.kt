package dmitry.example

import dmitry.example.GaussianBlurExample.inImagePath
import dmitry.example.GaussianBlurExample.outImagePath
import dmitry.filter.matrix.GaussianBlur
import dmitry.store.ImageLoader
import dmitry.store.ImageSaver

private object GaussianBlurExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<GaussianBlurExample>("original")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<GaussianBlurExample>("blurred")
}

fun main() {
    val blurredImage = GaussianBlur(ImageLoader.load(inImagePath)).convert()
    ImageSaver(blurredImage, outImagePath).save()
}
