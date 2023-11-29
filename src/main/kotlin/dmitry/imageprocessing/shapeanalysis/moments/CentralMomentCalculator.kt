package dmitry.imageprocessing.shapeanalysis.moments

import dmitry.imageprocessing.matrix.Matrix
import kotlin.math.pow

class CentralMomentCalculator(
    private val imageMatrix: Matrix<Int>,
    private val rawMomentCalculator: RawMomentCalculator
) : CachedMomentCalculator() {

    override fun calculateNonCachedMoment(i: Int, j: Int): Double {
        var result = .0

        val intensitySum = rawMomentCalculator.calculate(0, 0)

        val averageX = rawMomentCalculator.calculate(1, 0) / intensitySum
        val averageY = rawMomentCalculator.calculate(0, 1) / intensitySum

        imageMatrix.forEachIndexed { x, y, intensity ->
            result += (x - averageX).pow(i) * (y - averageY).pow(j) * intensity
        }

        return result
    }
}
