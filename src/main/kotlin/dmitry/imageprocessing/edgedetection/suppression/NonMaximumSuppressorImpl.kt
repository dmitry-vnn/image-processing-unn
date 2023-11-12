package dmitry.imageprocessing.edgedetection.suppression

import dmitry.imageprocessing.edgedetection.gradient.GradientCalculator
import dmitry.imageprocessing.edgedetection.gradient.GradientDirection
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.mapToNew
import java.awt.image.BufferedImage
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.sqrt


class NonMaximumSuppressorImpl(
    private val image: BufferedImage,
    private val gradientCalculator: GradientCalculator,
): NonMaximumSuppressor {

    override fun suppressNonMaximum() =
        image.mapToNew {x, y -> calculateColorWithNonMaximumSuppression(x, y) }

    private fun calculateColorWithNonMaximumSuppression(x: Int, y: Int): PixelColor {

        image.apply {
            val dx = gradientCalculator.gradientX(x, y)
            val dy = gradientCalculator.gradientY(x, y)

            val gradientAngle = calculateVectorAngleDegrees(dx, dy)

            val gradientDirection = calculateGradientDirection(gradientAngle)

            val neighboursIntensity = calculateNeighboursIntensity(
                x, y, gradientDirection)

            val intensity = intensity(x, y)

            if (neighboursIntensity.isNotEmpty() && intensity < neighboursIntensity.max()) {
                return PixelColor.fromRGB(0, 0, 0)
            }

            return PixelColor.fromBoundedGrayscale(intensity)
        }

    }

    private fun calculateGradientDirection(gradientAngle: Double): GradientDirection {
        return when {
            gradientAngle >= -90.0 && gradientAngle < -67.5 -> GradientDirection.DOWN
            gradientAngle >= -67.5 && gradientAngle < -22.5 -> GradientDirection.RIGHT_TO_DOWN_DIAGONAL
            gradientAngle >= -22.5 && gradientAngle < 22.5 -> GradientDirection.RIGHT
            gradientAngle >= 22.5 && gradientAngle < 67.5 -> GradientDirection.UP_TO_RIGHT_DIAGONAL
            gradientAngle >= 67.5 && gradientAngle <= 90.0 -> GradientDirection.UP

            else -> throw IllegalArgumentException("gradientAngle must be inclusive in [-90..90] degrees")
        }
    }

    private fun calculateVectorAngleDegrees(x: Int, y: Int): Double {

        if (x == 0) {
            return if (y < 0) -90.0 else 90.0
        }

        val angleRadian = atan(y.toDouble() / x)

        return angleRadian * (180 / PI)
    }

    private fun BufferedImage.calculateNeighboursIntensity(x: Int, y: Int, gradientDirection: GradientDirection): List<Int> {
        val neighboursIntensity = mutableListOf<Int>()

        fun addNeighboursIntensity(
            x: Int,
            y: Int
        ) {
            val value = intensityOrNull(x, y)
            if (value != null) {
                neighboursIntensity.add(value)
            }
        }

        when (gradientDirection) {
            GradientDirection.DOWN, GradientDirection.UP -> {
                addNeighboursIntensity(x, y + 1)
                addNeighboursIntensity(x, y - 1)
            }

            GradientDirection.UP_TO_RIGHT_DIAGONAL -> {
                addNeighboursIntensity(x + 1, y + 1)
                addNeighboursIntensity(x - 1, y - 1)
            }

            GradientDirection.RIGHT -> {
                addNeighboursIntensity(x + 1, y)
                addNeighboursIntensity(x - 1, y)
            }

            GradientDirection.RIGHT_TO_DOWN_DIAGONAL -> {
                addNeighboursIntensity(x + 1, y - 1)
                addNeighboursIntensity(x - 1, y + 1)
            }
        }

        return neighboursIntensity
    }

    private fun BufferedImage.intensityOrNull(x: Int, y: Int): Int? {
        if (x !in 0..<width - 1 || y !in 0..<height - 1) {
            return null
        }

        return intensity(x, y)
    }

    private fun intensity(x: Int, y: Int): Int {

        gradientCalculator.apply {
            val dx = gradientX(x, y)
            val dy = gradientY(x, y)

            return sqrt((dx * dx + dy * dy).toDouble()).toInt()
        }
    }


}




