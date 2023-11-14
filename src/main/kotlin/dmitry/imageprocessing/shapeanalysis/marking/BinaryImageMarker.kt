package dmitry.imageprocessing.shapeanalysis.marking

import dmitry.imageprocessing.shapeanalysis.matrix.Matrix

interface BinaryImageMarker {

    fun mark(): Matrix<Int>
    fun shapeMarkedCount(): Int

}