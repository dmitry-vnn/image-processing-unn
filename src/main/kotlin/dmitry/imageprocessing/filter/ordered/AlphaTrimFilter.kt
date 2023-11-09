package dmitry.imageprocessing.filter.ordered

import dmitry.imageprocessing.filter.ImageConverter
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
                image.setRGB(x, y, calculateAlphaTrimColor(x, y).rgb)
            }

        return image
    }

    private fun calculateAlphaTrimColor(x: Int, y: Int): Color {

        val window = createPixelsWindowAroundPoint(x, y)

        val needPixelsToSum = window.size - 2 * trimValue

        if (needPixelsToSum == 0) {
            return Color.BLACK
        }

        if (needPixelsToSum < 0) {
            return Color(image.getRGB(x, y))
        }

        window.sortWith(fun(color1: Color, color2: Color): Int {
            val redCompare = color1.red compareTo color2.red

            if (redCompare != 0) {
                return redCompare
            }

            val greenCompare = color1.green compareTo color2.green

            if (greenCompare != 0) {
                return greenCompare
            }

            return color1.blue compareTo color2.blue
        })

        var componentsSumOfRemainingPixels = Triple(0, 0, 0)
        
        for (i in trimValue..<window.size - trimValue)
            componentsSumOfRemainingPixels += window[i]

        return (componentsSumOfRemainingPixels / needPixelsToSum).toColor()

    }

    private fun createPixelsWindowAroundPoint(x: Int, y: Int): ArrayList<Color> {
        val windowPixels = ArrayList<Color>(windowSquare)

        val halfSideSize = windowSideSize / 2

        for (j in -halfSideSize..halfSideSize)
            for (i in -halfSideSize..halfSideSize) {
                val windowPixelX = x + i
                val windowPixelY = y + j
                if (image.isInsidePoint(windowPixelX, windowPixelY)) {
                    windowPixels += Color(image.getRGB(windowPixelX, windowPixelY))
                }
            }

        return windowPixels
    }

}

private fun Triple<Int, Int, Int>.toColor() =
    Color(first, second, third)

private operator fun Triple<Int, Int, Int>.div(divider: Int) =
    Triple(first / divider, second / divider, third / divider)

private operator fun Triple<Int, Int, Int>.plus(color: Color) =
    Triple(first + color.red, second + color.green, third + color.blue)

private fun BufferedImage.isInsidePoint(windowPixelX: Int, windowPixelY: Int): Boolean {
    return windowPixelX in 0..<width && windowPixelY in 0..<height
}
