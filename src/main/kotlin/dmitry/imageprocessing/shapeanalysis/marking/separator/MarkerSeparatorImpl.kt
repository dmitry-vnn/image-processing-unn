package dmitry.imageprocessing.shapeanalysis.marking.separator

import dmitry.imageprocessing.shapeanalysis.marking.BinaryImageMarker
import dmitry.imageprocessing.matrix.IntMatrix
import dmitry.imageprocessing.matrix.Matrix

class MarkerSeparatorImpl(
    private val binaryImageMarker: BinaryImageMarker
) : MarkerSeparator {

    override fun separate(): List<Matrix<Int>> {

        val markedMatrix = binaryImageMarker.mark()
        val componentsCount = binaryImageMarker.shapeMarkedCount()

        val separatedComponents = List(componentsCount) { IntMatrix(markedMatrix.height, markedMatrix.width) }

        markedMatrix.forEachIndexed {x, y, marker ->

            if (marker != 0) {
                separatedComponents[marker - 1][y, x] = 1
            }

        }

        return separatedComponents

    }
}