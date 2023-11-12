package dmitry.imageprocessing.edgedetection

import dmitry.imageprocessing.edgedetection.gradient.GradientMagnitude
import dmitry.imageprocessing.edgedetection.gradient.SobelGradientCalculator
import dmitry.imageprocessing.edgedetection.suppression.NonMaximumSuppressorBest
import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilterImpl
import dmitry.imageprocessing.edgedetection.tracking.EdgeTrackerHysteresis
import dmitry.imageprocessing.filter.matrix.GaussianBlur
import dmitry.imageprocessing.filter.point.MonochromeGrayConverter
import java.awt.image.BufferedImage

class CannyOperator(private val image: BufferedImage) {

    fun detectEdges(): BufferedImage {
        var image = image
        image = MonochromeGrayConverter(image).convert()
        image = GaussianBlur(image, 5.0, 5).convert()
        val gradientCalculator = SobelGradientCalculator(image)
        image = GradientMagnitude(image, gradientCalculator).apply()
        image = NonMaximumSuppressorBest(image, gradientCalculator).suppressNonMaximum()
        image = DoubleThresholdFilterImpl(image).threshold(0.1, 0.2)
        image = EdgeTrackerHysteresis(image).track()

        return image

    }

}