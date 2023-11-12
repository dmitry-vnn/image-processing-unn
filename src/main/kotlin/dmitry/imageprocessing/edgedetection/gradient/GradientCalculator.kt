package dmitry.imageprocessing.edgedetection.gradient

import kotlin.math.sqrt

interface GradientCalculator {

    fun intensity(x: Int, y: Int): Double {
        val dx = gradientX(x, y)
        val dy = gradientY(x, y)
        return sqrt((dx*dx + dy*dy).toDouble())
    }

    fun gradientX(x: Int, y: Int): Int
    fun gradientY(x: Int, y: Int): Int

}