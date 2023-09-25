package dmitry.metric

import java.awt.image.BufferedImage
import kotlin.math.pow

class ImageUQIMetric(basicImage: BufferedImage, comparableImage: BufferedImage) : AbstractImagesComparator(basicImage, comparableImage) {

    override fun compareGrayMonochromeImages(): Double {

        val square = basicMonochromeImage.let { it.width * it.height }

        val averageColors = calculateAverageColors(square)
        val deviationColors = calculateDeviationColors(square, averageColors)

        val correlation = calculateCorrelation(square, averageColors)

        return (4 * correlation * averageColors.first * averageColors.second) /
                ( ( deviationColors.first + deviationColors.second ) * (averageColors.first.pow(2) + averageColors.second.pow(2)))

    }

    private fun calculateCorrelation(square: Int, averageColors: Pair<Double, Double>): Double {
        var correlation = 0.0

        for (x in 0..<basicMonochromeImage.width) {
            for (y in 0..<basicMonochromeImage.height) {
                correlation += (basicMonochromeImage.takeColor(x, y) - averageColors.first) * (comparableMonochromeImage.takeColor(x, y) - averageColors.second)
            }
        }

        correlation /= square

        return correlation
    }

    private fun calculateAverageColors(square: Int): Pair<Double, Double> {
        var averageBasicImageColor = 0.0
        var averageComparableImageColor = 0.0

        for (x in 0..<basicMonochromeImage.width) {
            for (y in 0..<basicMonochromeImage.height) {
                averageBasicImageColor += basicMonochromeImage.takeColor(x, y)
                averageComparableImageColor += comparableMonochromeImage.takeColor(x, y)
            }
        }

        averageBasicImageColor /= square
        averageComparableImageColor /= square

        return Pair(averageBasicImageColor, averageComparableImageColor)
    }

    private fun calculateDeviationColors(square: Int, averageColors: Pair<Double, Double>): Pair<Double, Double> {
        var deviationBasicImageColor = 0.0
        var deviationComparableImageColor = 0.0

        for (x in 0..<basicMonochromeImage.width) {
            for (y in 0..<basicMonochromeImage.height) {
                deviationBasicImageColor += (basicMonochromeImage.takeColor(x, y) - averageColors.first).pow(2)
                deviationComparableImageColor += (comparableMonochromeImage.takeColor(x, y) - averageColors.second).pow(2)
            }
        }

        deviationBasicImageColor /= square
        deviationComparableImageColor /= square

        return Pair(deviationBasicImageColor, deviationComparableImageColor)
    }

}