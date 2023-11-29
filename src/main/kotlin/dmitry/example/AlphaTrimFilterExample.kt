package dmitry.example

import dmitry.example.AlphaTrimFilterExample.inImagePath
import dmitry.example.AlphaTrimFilterExample.outImagePath
import dmitry.imageprocessing.filter.noisesuppression.ordered.AlphaTrimFilter
import dmitry.imageprocessing.store.ImageLoader
import dmitry.imageprocessing.store.ImageSaver

private object AlphaTrimFilterExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<AlphaTrimFilterExample>("noised1", "bmp")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<AlphaTrimFilterExample>("filtered1")
}

fun main() {
    val filtered = AlphaTrimFilter(ImageLoader.load(inImagePath), 5, 5).convert()
    ImageSaver(filtered, outImagePath).save()
}
