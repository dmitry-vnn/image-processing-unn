package dmitry.imageprocessing.shapeanalysis.moments

import kotlin.math.PI
import kotlin.math.atan

class ImagePropertiesCalculator(
    rawMomentCalculator: MomentCalculator,
    centralMomentCalculator: MomentCalculator
) {

    private val raw = {i: Int, j: Int -> rawMomentCalculator.calculate(i, j)}
    private val central = {i: Int, j: Int -> centralMomentCalculator.calculate(i, j)}

    fun centroid(): Pair<Double, Double> {
        return raw(1, 0) / square() to
               raw(0, 1) / square()
    }

    fun square(): Double {
        // intensity sum represent a square
        return raw(0, 0)
    }

    fun orientationAngleDegree(): Double {

        return 180/PI * .5 * atan(
            (2 * central(1, 1)) /
               (central(2, 0) - central(0, 2))

        )

    }

}