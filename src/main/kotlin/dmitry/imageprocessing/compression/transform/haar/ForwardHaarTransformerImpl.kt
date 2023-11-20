package dmitry.imageprocessing.compression.transform.haar

import dmitry.imageprocessing.compression.FrequencyContainer
import dmitry.imageprocessing.util.ImageExtensions.iterator
import java.awt.image.BufferedImage
import kotlin.math.ceil
import kotlin.math.sqrt

class ForwardHaarTransformerImpl(
    private val image: BufferedImage
): ForwardHaarTransformer {

    override fun groupByFrequencies(): FrequencyContainer {
        image.apply {
            val pairsCount = ceil(width * height.toDouble() / 2).toInt()

            val lowerFrequencies: MutableList<Double> = ArrayList(pairsCount)
            val upperFrequencies = ArrayList<Double>(pairsCount)

            val pixelIterator = iterator()
            while (pixelIterator.hasNext()) {
                val intensity = pixelIterator.next().grayscale.toDouble()

                if (!pixelIterator.hasNext()) {
                    lowerFrequencies += intensity
                    upperFrequencies += intensity
                    break
                }

                val nextIntensity = pixelIterator.next().grayscale.toDouble()

                lowerFrequencies += (nextIntensity - intensity) / sqrt(2.0)
                upperFrequencies += (nextIntensity + intensity) / sqrt(2.0)
            }

            return FrequencyContainer(
                height, width,
                lowerFrequencies, upperFrequencies
            )
        }
    }

}