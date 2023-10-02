package dmitry.metric

import dmitry.ImageRegion
import dmitry.converter.MonochromeGrayConverter
import java.awt.Color
import java.awt.image.BufferedImage

abstract class AbstractImagesComparator(
    protected val basicMonochromeImage: BufferedImage,
    protected val comparableMonochromeImage: BufferedImage,
    protected val imageRegion: ImageRegion = ImageRegion(basicMonochromeImage)
): ImageMetric  {

    init {
        assertSameImagesSize()
    }

    private fun assertSameImagesSize() {
        if (!(imageRegion.regionStart.x >= 0 && imageRegion.regionStart.y >= 0  &&
            imageRegion.regionEnd.x < basicMonochromeImage.width && imageRegion.regionEnd.x < comparableMonochromeImage.width &&
            imageRegion.regionEnd.y < basicMonochromeImage.height && imageRegion.regionEnd.y < comparableMonochromeImage.height
            ))
            throw IllegalArgumentException("imageRegion must be include in both basic image and comparable image")
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