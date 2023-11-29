package dmitry.imageprocessing.shapeanalysis.moments

import dmitry.imageprocessing.matrix.Matrix
import kotlin.math.pow

class RawMomentCalculator(
    private val imageMatrix: Matrix<Int>
) : CachedMomentCalculator() {

    override fun calculateNonCachedMoment(i: Int, j: Int): Double {
        var result = .0

        imageMatrix.forEachIndexed { x, y, intensity ->
            result += x.pow(i) * y.pow(j) * intensity
        }

        return result
    }

    private fun Int.pow(power: Int): Long {
        return toDouble().pow(power.toDouble()).toLong()
    }
}
