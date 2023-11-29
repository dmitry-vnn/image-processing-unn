package dmitry.imageprocessing.shapeanalysis.marking.separator

import dmitry.imageprocessing.matrix.Matrix

interface MarkerSeparator {

    fun separate(): List<Matrix<Int>>

}
