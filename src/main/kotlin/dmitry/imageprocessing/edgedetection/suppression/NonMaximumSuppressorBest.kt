package dmitry.imageprocessing.edgedetection.suppression

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.edgedetection.gradient.GradientCalculator
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.contains
import dmitry.imageprocessing.util.ImageExtensions.emptyCopy
import dmitry.imageprocessing.util.ImageExtensions.forEach
import dmitry.imageprocessing.util.ImageExtensions.mapToNew
import java.awt.image.BufferedImage
import kotlin.math.*


class NonMaximumSuppressorBest(
    private val image: BufferedImage,
    private val gradientCalculator: GradientCalculator,
): NonMaximumSuppressor {

    override fun suppressNonMaximum(): BufferedImage {
        val result = image.emptyCopy()

        image.forEach { x, y ->

            val dx = gradientCalculator.gradientX(x, y)
            val dy = gradientCalculator.gradientY(x, y)

            val gradientAngle = calculateVectorAngle(dx, dy)

            val neighbourPoints = image.neighboursPoints(
                x, y, gradientAngle)

            val intensity = intensity(x, y)

            val neighbourIntensities = neighbourPoints.map { intensity(it.x, it.y) }

            if (neighbourIntensities.isEmpty() || neighbourIntensities.max() > intensity) {
                result.setRGB(x, y, PixelColor.fromRGB(0, 0, 0).rgb)
            } else {
                result.setRGB(x, y, PixelColor.fromBoundedGrayscale(intensity).rgb)
            }

            //for (point in neighbourPoints) {
            //
            //    val (nx, ny) = point
            //
            //    if (intensity(nx, ny) <= intensity) {
            //        result.setRGB(nx, ny, PixelColor.fromRGB(0, 0, 0).rgb)
            //    }
            //}

            //result.setRGB(x, y, PixelColor.fromBoundedGrayscale(intensity).rgb)

        }

        return result

    }

    private fun calculateVectorAngle(x: Int, y: Int): Double {
        return round(atan2(x.toDouble(), y.toDouble()) / (PI / 4)) * (PI / 4) - PI / 2
    }

    private fun BufferedImage.neighboursPoints(x: Int, y: Int, gradientAngle: Double): List<Point> {
        val neighboursIntensity = mutableListOf<Point>()

        fun addNeighboursIntensity(
            x: Int,
            y: Int
        ) {
            val point = Point(x, y)

            if (point in this) {
                neighboursIntensity += point
            }

        }

        val dxInDirection = sign(cos(gradientAngle)).toInt()
        val dyInDirection = sign(sin(gradientAngle)).toInt()

        addNeighboursIntensity(x + dxInDirection, y + dyInDirection)
        addNeighboursIntensity(x - dxInDirection, y - dyInDirection)

        return neighboursIntensity
    }

    private fun intensity(x: Int, y: Int): Int {

        gradientCalculator.apply {
            val dx = gradientX(x, y)
            val dy = gradientY(x, y)

            return sqrt((dx * dx + dy * dy).toDouble()).toInt()
        }
    }

}