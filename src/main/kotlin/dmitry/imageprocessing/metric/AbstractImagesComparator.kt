package dmitry.imageprocessing.metric

import dmitry.imageprocessing.ImageRegion
import dmitry.imageprocessing.filter.point.MonochromeGrayConverter
import java.awt.Color
import java.awt.image.BufferedImage

abstract class AbstractImagesComparator(
    protected val basicMonochromeImage: BufferedImage,
    protected val comparableMonochromeImage: BufferedImage,
    protected val imageRegion: ImageRegion = ImageRegion(basicMonochromeImage)
): ImageMetric {

    init {
        assertSameImagesSize()
    }

    private fun assertSameImagesSize() {
        if (!(basicMonochromeImage.width == comparableMonochromeImage.width
            && comparableMonochromeImage.height == comparableMonochromeImage.height)) {
            throw IllegalArgumentException("basic and comparable images must be same size")
        }
    }

    override fun calculate(): Double {

        fun convertImagesToGrayMonochrome() {
            MonochromeGrayConverter(basicMonochromeImage, imageRegion).convert()
            MonochromeGrayConverter(comparableMonochromeImage, imageRegion).convert()
        }

        convertImagesToGrayMonochrome()

        return compareGrayMonochromeImages()

    }

    protected fun BufferedImage.takeColor(x: Int, y: Int): Int {
        return Color(getRGB(x, y)).red
    }

    protected abstract fun compareGrayMonochromeImages(): Double
}