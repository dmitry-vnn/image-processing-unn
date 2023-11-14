package dmitry.imageprocessing.shapeanalysis.moments

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.shapeanalysis.matrix.Matrix
import kotlin.math.pow

abstract class CachedMomentCalculator: MomentCalculator {

    private val cache = HashMap<Point, Double>(10)

    override fun calculate(i: Int, j: Int): Double {

        val point = Point(i, j)

        val cachedValue = cache[point]

        if (cachedValue != null) {
            return cachedValue
        }

        return calculateNonCachedMoment(i, j)
            .also { cache[point] = it }
    }

    protected abstract fun calculateNonCachedMoment(i: Int, j: Int): Double
}
