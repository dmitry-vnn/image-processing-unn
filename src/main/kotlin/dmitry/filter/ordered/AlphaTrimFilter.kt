package dmitry.filter.ordered

import dmitry.filter.ImageConverter
import java.awt.image.BufferedImage

class AlphaTrimFilter(
    private val image: BufferedImage,
    private val windowSideSize: UInt,
    private val trimValue: UInt
): ImageConverter {

    private val windowSquare = windowSideSize * windowSideSize

    init {

        if (windowSquare > (image.width * image.height).toUInt()) {
            throw IllegalArgumentException("window square must be <= then image square")
        }

        if (windowSquare <= 2u * trimValue) {
            throw IllegalArgumentException("windows square must be > then trim value")
        }
    }

    override fun convert(): BufferedImage {
        for (y in 0..<image.height)
            for (x in 0..<image.width)
    }
}