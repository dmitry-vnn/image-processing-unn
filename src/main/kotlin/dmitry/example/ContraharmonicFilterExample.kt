package dmitry.example

import dmitry.example.ContraharmonicFilterExample.inImagePath
import dmitry.example.ContraharmonicFilterExample.outImagePath
import dmitry.imageprocessing.filter.noisesuppression.smooth.ContraharmonicFilter
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver

private object ContraharmonicFilterExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<ContraharmonicFilterExample>("noised", "bmp")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<ContraharmonicFilterExample>("filtered")
}

fun main() {
    val filtered = ContraharmonicFilter(ImageLoader.load(inImagePath), 5, 1.0).convert()
    ImageSaver(filtered, outImagePath).save()
}
