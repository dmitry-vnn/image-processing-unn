package dmitry.metric

import dmitry.converter.MonochromeGrayConverter
import java.awt.Color
import java.awt.image.BufferedImage

abstract class AbstractImagesComparator(protected val basicMonochromeImage: BufferedImage, protected val comparableMonochromeImage: BufferedImage): ImageMetric  {

    init {
        assertSameImagesSize()
    }

    private fun assertSameImagesSize() {
        if (basicMonochromeImage.height != comparableMonochromeImage.height || basicMonochromeImage.width != comparableMonochromeImage.width)
            throw IllegalArgumentException("images must be same size")
    }

    override fun calculate(): Double {

        fun convertImagesToGrayMonochrome() {
            MonochromeGrayConverter(basicMonochromeImage).convert()
            MonochromeGrayConverter(comparableMonochromeImage).convert()
        }

        convertImagesToGrayMonochrome()

        return compareGrayMonochromeImages();

    }

    protected fun BufferedImage.takeColor(x: Int, y: Int): Int {
        return Color(getRGB(x, y)).red
    }

    abstract fun compareGrayMonochromeImages(): Double
}