package dmitry.imageprocessing.shapeanalysis.marking

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.shapeanalysis.matrix.IntMatrix
import dmitry.imageprocessing.util.ImageExtensions.contains
import dmitry.imageprocessing.util.ImageExtensions.forEach
import dmitry.imageprocessing.util.ImageExtensions.getPixelColor
import java.awt.image.BufferedImage

class RecursiveBinaryImageMarker(
    private val binaryImage: BufferedImage
): BinaryImageMarker {

    var shapes = 0

    override fun mark(): IntMatrix {

        val markedMatrix = IntMatrix(binaryImage.height, binaryImage.width)

        var marker = 1

        binaryImage.forEach { x, y ->

            val pixelColor = binaryImage.getPixelColor(x, y)
            if (pixelColor.grayscale == 255) {
                if (binaryImage.canRunRecursiveMark(x, y, markedMatrix, marker)) {
                    marker++
                }
            }

        }

        shapes = marker - 1

        return markedMatrix

    }

    override fun shapeMarkedCount(): Int {
        return shapes
    }
}

private fun BufferedImage.canRunRecursiveMark(x: Int, y: Int, markedMatrix: IntMatrix, marker: Int): Boolean {
    if (markedMatrix[y, x] != 0) {
        return false
    }

    recursiveMark(x, y, markedMatrix, marker)

    return true
}

private fun BufferedImage.recursiveMark(x: Int, y: Int, markedMatrix: IntMatrix, marker: Int) {

    if (Point(x, y) !in this) {
        return
    }

    if (markedMatrix[y, x] != 0) {
        return
    }

    if (getPixelColor(x, y).grayscale != 255) {
        return
    }

    markedMatrix[y, x] = marker

    for (dx in -1..1)
        for (dy in -1..1)
            if (dx != 0 || dy != 0)
                recursiveMark(x + dx, y + dy, markedMatrix, marker)

}
