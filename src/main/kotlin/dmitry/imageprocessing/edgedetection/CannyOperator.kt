package dmitry.imageprocessing.edgedetection

import dmitry.imageprocessing.edgedetection.gradient.GradientMagnitude
import dmitry.imageprocessing.edgedetection.gradient.SobelGradientCalculator
import dmitry.imageprocessing.edgedetection.suppression.NonMaximumSuppressorBest
import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilter
import dmitry.imageprocessing.edgedetection.threshold.DoubleThresholdFilterImpl
import dmitry.imageprocessing.edgedetection.tracking.EdgeTrackerHysteresis
import dmitry.imageprocessing.filter.matrix.GaussianBlur
import dmitry.imageprocessing.filter.point.MonochromeGrayConverter
import java.awt.image.BufferedImage

class CannyOperator(
    private val image: BufferedImage,
    private val gaussianBlurSigma: Double = 5.0,
    private val gaussianBlurKernelSize: Int = 3,
    private val lowerThresholdPercentage: Double = 0.5,
    private val upperThresholdPercentage: Double = 0.6,
) {

    fun detectEdges(): BufferedImage {
        var image = image
        image = MonochromeGrayConverter(image).convert()
        image = GaussianBlur(image, gaussianBlurSigma, gaussianBlurKernelSize).convert()
        val gradientCalculator = SobelGradientCalculator(image)
        image = GradientMagnitude(image, gradientCalculator).apply()
        image = NonMaximumSuppressorBest(image, gradientCalculator).suppressNonMaximum()
        image = DoubleThresholdFilterImpl(image,
            lowerThresholdPercentage, upperThresholdPercentage).threshold()
        image = EdgeTrackerHysteresis(image).track()

        return image

    }

}