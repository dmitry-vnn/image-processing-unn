package dmitry.metric

import dmitry.converter.MonochromeGrayConverter
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow


class ImageMSEMetric(private val basicImage: BufferedImage, private val comparableImage: BufferedImage) : ImageMetric {

    init {
        assertSameImagesSize()
    }

    private fun assertSameImagesSize() {
        if (basicImage.height != comparableImage.height || basicImage.width != comparableImage.width)
            throw IllegalArgumentException("images must be same size")
    }

    private fun convertImagesToGrayMonochrome() {
        MonochromeGrayConverter(basicImage).convert()
        MonochromeGrayConverter(comparableImage).convert()
    }

    override fun calculate(): Double {

        convertImagesToGrayMonochrome();

        var result = 0.0

        for (y in 0..<basicImage.height) {
            for (x in 0..<basicImage.width) {
                result += ((basicImage.takeColor(x, y) - comparableImage.takeColor(x, y)).toDouble()).pow(2.0)
            }
        }

        result /= basicImage.height * basicImage.width

        return result
    }


    private fun BufferedImage.takeColor(x: Int, y: Int): Int {
        return Color(getRGB(x, y)).red
    }

}

