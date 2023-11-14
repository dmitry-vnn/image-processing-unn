package dmitry.example

import dmitry.example.ComponentsExample.inImagePath
import dmitry.imageprocessing.shapeanalysis.marking.RecursiveBinaryImageMarker
import dmitry.imageprocessing.shapeanalysis.marking.separator.MarkerSeparatorImpl
import dmitry.imageprocessing.store.ImageLoader

private object ComponentsExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<ComponentsExample>("in", "png")
}

fun main() {
    val originalImage = ImageLoader.load(inImagePath)

    val separatedComponentsMasks = MarkerSeparatorImpl(RecursiveBinaryImageMarker(originalImage)).separate()

    println("Shapes detected: ${separatedComponentsMasks.size}")

}
