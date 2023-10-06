package dmitry.example

import dmitry.example.AlphaTrimFilterExample.inImagePath
import dmitry.example.AlphaTrimFilterExample.outImagePath
import dmitry.filter.ordered.AlphaTrimFilter
import dmitry.store.ImageLoader
import dmitry.store.ImageSaver

private object AlphaTrimFilterExample {
    val inImagePath = PathToExampleEvaluator.evaluatePath<AlphaTrimFilterExample>("noised.bmp")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<AlphaTrimFilterExample>("filtered")
}

fun main() {
    val filtered = AlphaTrimFilter(ImageLoader(inImagePath).load(), 5, 5).convert()
    ImageSaver(filtered, outImagePath).save()
}
