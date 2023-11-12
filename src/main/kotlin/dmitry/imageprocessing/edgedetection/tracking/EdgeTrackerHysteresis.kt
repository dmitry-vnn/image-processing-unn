package dmitry.imageprocessing.edgedetection.tracking

import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilter.Companion.LOWER_COLOR
import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilter.Companion.UPPER_COLOR
import dmitry.imageprocessing.util.ImageExtensions.forEach
import java.awt.image.BufferedImage

class EdgeTrackerHysteresis(
    private val image: BufferedImage
): EdgeTracker {

    private val xBound = 0..<image.width
    private val yBound = 0..<image.height

    private val directions = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to 1,
        0 to -1,
        1 to -1,
        1 to 0,
        1 to 1
    )

    override fun track(): BufferedImage {

        val resultImage = image.apply {
            BufferedImage(width, height, type)
        }

        resultImage.forEach { x, y ->
            val color = image.getRGB(x, y)

            if (color != UPPER_COLOR) {
                resultImage.setRGB(x, y, LOWER_COLOR)
                return@forEach
            }

            resultImage.setRGB(x, y, UPPER_COLOR)

            for ((dx, dy) in directions) {
                var offsetX = x
                var offsetY = y

                while (true) {
                    offsetX += dx
                    offsetY += dy

                    if (offsetX !in xBound || offsetY !in yBound) {
                        break
                    }

                    val colorByDirection = image.getRGB(offsetX, offsetY)

                    if (colorByDirection == UPPER_COLOR || colorByDirection == LOWER_COLOR) {
                        break
                    }

                    resultImage.setRGB(x, y, UPPER_COLOR)

                }
            }
        }

        return resultImage

    }


    class Direction(private val dx: Int, private val dy: Int) {
        operator fun component1() = dx
        operator fun component2() = dy
    }

    private infix fun Int.to(dy: Int) = Direction(this, dy)
}