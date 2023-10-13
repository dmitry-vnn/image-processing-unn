package dmitry.noise.additive

import dmitry.noise.AdditiveNoiseGenerator
import java.awt.image.BufferedImage
import kotlin.math.*

class GaussianNoiseGenerator(
    m: Double,
    sigma: Double,

    noisePercentage: Double,
    image: BufferedImage,

): AdditiveNoiseGenerator(
    image,
    noisePercentage,
    GaussianNoiseProbabilityCreator.createGrayscaleProbability(sigma, m)
)

object GaussianNoiseProbabilityCreator {

    fun createGrayscaleProbability(sigma: Double, m: Double): DoubleArray {
        val probability = DoubleArray(255)

        var sum = 0.0

        for (i in probability.indices) {
            probability[i] = (1 / (sqrt(2 * PI) * sigma))  * E.pow( (i - m).pow(2) / -2 * sigma.pow(2) )
            sum += probability[i]
        }

        for (i in probability.indices) {
            probability[i] /= sum
        }

        return probability
    }

}


