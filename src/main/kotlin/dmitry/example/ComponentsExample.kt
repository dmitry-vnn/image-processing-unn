package dmitry.example

import dmitry.example.ComponentsExample.inImagePath
import dmitry.imageprocessing.shapeanalysis.marking.RecursiveBinaryImageMarker
import dmitry.imageprocessing.shapeanalysis.marking.separator.MarkerSeparatorImpl
import dmitry.imageprocessing.shapeanalysis.moments.CentralMomentCalculator
import dmitry.imageprocessing.shapeanalysis.moments.ImagePropertiesCalculator
import dmitry.imageprocessing.shapeanalysis.moments.RawMomentCalculator
import dmitry.imageprocessing.store.ImageLoader

private object ComponentsExample {
    val inImagePath = PathToExampleEvaluator.evaluateImagePath<ComponentsExample>("in", "png")
}

fun main() {
    val originalImage = ImageLoader.load(inImagePath)

    val separatedComponentsMasks = MarkerSeparatorImpl(RecursiveBinaryImageMarker(originalImage)).separate()

    separatedComponentsMasks.forEachIndexed {componentIndex, componentBinaryImage ->

        val rawMomentCalculator = RawMomentCalculator(componentBinaryImage)
        val propertiesCalculator = ImagePropertiesCalculator(
            rawMomentCalculator,
            CentralMomentCalculator(componentBinaryImage,
                rawMomentCalculator)
        )


        println("shape #${componentIndex + 1}:")
        println(" center = " + propertiesCalculator.centroid() )
        println(" square = " + propertiesCalculator.square() )
        println(" orientation angle = " + propertiesCalculator.orientationAngleDegree() )

    }
    println("Shapes detected: ${separatedComponentsMasks.size}")

}
