package dmitry.noise.additive

import dmitry.noise.AdditiveNoiseGenerator
import java.awt.image.BufferedImage
import kotlin.math.*

class GaussianNoiseGenerator(
    m: Double,
    sigma: Double,

    noisePercentage: Double,
    image: BufferedImage,

): AdditiveNoiseGenerator(image, noisePercentage, DoubleArray(255)) {

    init {
        var sum = 0.0

        for (i in normalizedProbabilityDistribution.indices) {
            normalizedProbabilityDistribution[i] = (1 / (sqrt(2 * PI) * sigma))  * E.pow( (i - m).pow(2) / -2 * sigma.pow(2) )
            sum += normalizedProbabilityDistribution[i]
        }

        for (i in normalizedProbabilityDistribution.indices) {
            normalizedProbabilityDistribution[i] /= sum
        }
    }
}


