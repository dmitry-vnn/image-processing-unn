package dmitry.filter.ordered

import dmitry.filter.ImageConverter
import java.awt.Color
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
                val grayscale = calculateAlphaTrimColor(x, y)
                image.setRGB(x, y, Color(grayscale, grayscale, grayscale).rgb)
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
            val color = Color(image.getRGB(x, y))
            return (color.red + color.green + color.blue) / 3
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
                    val rgb = image.getRGB(windowPixelX, windowPixelY)
                    val color = Color(rgb)
                    
                    windowPixels += (color.red + color.green + color.blue) / 3
                }
            }

        return windowPixels
    }

}

private fun BufferedImage.isInsidePoint(windowPixelX: Int, windowPixelY: Int): Boolean {
    return windowPixelX in 0..<width && windowPixelY in 0..<height
}
