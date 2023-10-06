package dmitry.filter.ordered

import dmitry.filter.ImageConverter
import java.awt.image.BufferedImage

class AlphaTrimFilter(
    private val image: BufferedImage,
    private val windowSideSize: Int,
    private val trimValue: Int
): ImageConverter {

    private val windowSquare = windowSideSize * windowSideSize

    init {


        if (windowSquare > image.width * image.height) {
            throw IllegalArgumentException("window square must be <= then image square")
        }

        if (windowSquare <= 2 * trimValue) {
            throw IllegalArgumentException("windows square must be > then trim value")
        }
    }

    override fun convert(): BufferedImage {
        for (y in 0..<image.height)
            for (x in 0..<image.width) {
                image.setRGB(x, y, calculateAlphaTrimColor(x, y))
            }

        return image
    }

    private fun calculateAlphaTrimColor(x: Int, y: Int): Int {


        val window = createPixelsWindowAroundPoint(x, y)

        val needPixelsToSum = window.size - 2 * trimValue

        if (needPixelsToSum == 0) {
            return 0;
        }

        if (needPixelsToSum < 0) {
            return image.getRGB(x, y)
        }


        window.sort()

        val sumOfRemainingPixels = window.drop(trimValue).dropLast(trimValue).sum()

        return sumOfRemainingPixels / needPixelsToSum //average value


    }

    private fun createPixelsWindowAroundPoint(x: Int, y: Int): ArrayList<Int> {
        val windowPixels = ArrayList<Int>(windowSquare)

        val halfSideSize = windowSideSize / 2

        for (j in -halfSideSize..halfSideSize)
            for (i in -halfSideSize..halfSideSize) {
                val windowPixelX = x + i
                val windowPixelY = y + j
                if (image.isInsidePoint(windowPixelX, windowPixelY)) {
                    windowPixels += image.getRGB(windowPixelX, windowPixelY)
                }
            }

        return windowPixels
    }

}

private fun BufferedImage.isInsidePoint(windowPixelX: Int, windowPixelY: Int): Boolean {
    return windowPixelX in 0..<width && windowPixelY in 0..<height
}
