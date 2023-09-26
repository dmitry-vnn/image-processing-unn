package dmitry.noise

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.random.Random
import kotlin.random.nextInt

class AdditiveNoiseGenerator(
    val image: BufferedImage,
    val normalizedProbabilityDistribution: DoubleArray,
    val noisePercentage: Double
): NoiseGenerator
{

    override fun generate(): BufferedImage {

        val points = takeNoisePoints()

        for ((x, y) in points) {
            var probabilityIndex = generateProbabilityIndex()

            image.setRGB(x, y, Color.WHITE.rgb)
        }

        return image
    }

    private fun generateProbabilityIndex(): Int {
        //Random number in range [0..1]
        val randomNumber = Random.nextDouble()

        var i = 0
        var sumBefore = 0.0;
        while (i < normalizedProbabilityDistribution.size) {

            val delta = randomNumber - sumBefore
            val probability = normalizedProbabilityDistribution;

            if (0 <= delta && delta <= normalizedProbabilityDistribution[i]) {
                sumBefore += probability
            }
        }
    }

    private fun takeNoisePoints(): Set<Pair<Int, Int>> {

        var pointsNeed = (noisePercentage * image.run { width * height }).toInt()

        val tookPoints = HashSet<Pair<Int, Int>>(pointsNeed)

        val allPoints = ArrayList<Pair<Int, Int>>(image.width * image.height)

        for (x in 0..<image.width) {
            for (y in 0..<image.height) {
                allPoints += Pair(x, y)
            }
        }

        val random = Random

        while (pointsNeed-- > 0) {
            val index = random.nextInt(0..<allPoints.size)
            tookPoints += allPoints[index]
            allPoints.removeAt(index)
        }

        return tookPoints

    }


}