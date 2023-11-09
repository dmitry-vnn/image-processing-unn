package dmitry.imageprocessing.noise.additive

import dmitry.imageprocessing.noise.AdditiveNoiseGenerator
import dmitry.imageprocessing.noise.GrayscaleProbabilityCreator
import java.awt.image.BufferedImage
import kotlin.math.*

class GaussianNoiseGenerator(
    sigma: Double,
    m: Double,

    noisePercentage: Double,
    image: BufferedImage,

): AdditiveNoiseGenerator(
    image,
    noisePercentage,
    GaussianNoiseProbabilityCreator(sigma, m).createNormalizedGrayscaleProbability()
)

class GaussianNoiseProbabilityCreator(
    private val sigma: Double,
    private val m: Double
): GrayscaleProbabilityCreator {

    override fun createGrayscaleProbability() =
        DoubleArray(255) { i ->
            (1 / (sqrt(2 * PI) * sigma)) * E.pow((i - m).pow(2) / -2 * sigma.pow(2))
        }
}


