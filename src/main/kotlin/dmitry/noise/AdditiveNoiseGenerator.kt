package dmitry.noise

import dmitry.util.ColorExtensions.bound
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.random.Random
import kotlin.random.nextInt

abstract class AdditiveNoiseGenerator(
    private val image: BufferedImage,
    private val noisePercentage: Double,
    private var normalizedProbabilityDistribution: DoubleArray
): NoiseGenerator
{

    override fun generate(): BufferedImage {

        var pointsNeedToNoise = (noisePercentage * image.width * image.height).toInt()

        while (pointsNeedToNoise-- > 0) {

            val (x, y) = takeNoisePoint()

            val probabilityIndex = generateProbabilityIndex()

            val colorWithNoise = Color(image.getRGB(x, y)) + Triple(probabilityIndex, probabilityIndex, probabilityIndex)
            image.setRGB(x, y, colorWithNoise.rgb)
        }

        return image
    }

    private fun takeNoisePoint(): Pair<Int, Int> {
        val x = Random.nextInt(0..<image.width)
        val y = Random.nextInt(0..<image.height)

        return Pair(x, y)
    }

    private fun generateProbabilityIndex(): Int {
        //Random number in range [0..1]
        val randomNumber = Random.nextDouble()

        var sumBefore = 0.0;

        for (i in normalizedProbabilityDistribution.indices) {
            val delta = randomNumber - sumBefore
            val probability = normalizedProbabilityDistribution[i];

            if (0 <= delta && delta <= normalizedProbabilityDistribution[i]) {
                return i
            }

            sumBefore += probability
        }

        throw IllegalStateException(
            "normalizedProbabilityDistribution values must be in range [0..1]"
        )
    }

    private fun takeNoisePoints(): Set<Pair<Int, Int>> {

        var pointsNeed = (noisePercentage * image.run { width * height }).toInt()

        val tookPoints = HashSet<Pair<Int, Int>>(pointsNeed)

        val totalPoints = ArrayList<Pair<Int, Int>>(image.width * image.height)

        for (x in 0..<image.width) {
            for (y in 0..<image.height) {
                totalPoints += Pair(x, y)
            }
        }

        val random = Random

        while (pointsNeed-- > 0) {
            val index = random.nextInt(0..<totalPoints.size)
            tookPoints += totalPoints[index]
            totalPoints.removeAt(index)
        }

        return tookPoints

    }


}

private operator fun Color.plus(triple: Triple<Int, Int, Int>): Color {
    val resultRed = bound(red + triple.first)
    val resultGreen = bound(green + triple.second)
    val resultBlue = bound(blue + triple.third)

    return Color(resultRed, resultGreen, resultBlue)
}

