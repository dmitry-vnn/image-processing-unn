package dmitry.imageprocessing.wavelet.apply

import dmitry.imageprocessing.util.ImageExtensions.BufferedImage
import dmitry.imageprocessing.util.ImageExtensions.forEachIndexed
import dmitry.imageprocessing.util.ImageExtensions.setPixelColor
import java.awt.image.BufferedImage

class ImageMatrixJoiner(
    row1col1: BufferedImage,
    row1col2: BufferedImage,
    row2col1: BufferedImage,
    row2col2: BufferedImage,
) {

    private val matrix = HashMap<Pair<Int, Int>, BufferedImage>()

    init {
        matrix[0 to 0] = row1col1
        matrix[0 to 1] = row1col2
        matrix[1 to 0] = row2col1
        matrix[1 to 1] = row2col2
    }
    
    fun join(): BufferedImage {
        matrix.values.first().apply {
            val image = BufferedImage(
                2 * width,
                2 * width,
            )

            matrix.forEach { (i, j), part ->
                part.forEachIndexed{ x, y, color ->
                    image.setPixelColor(i * width + x, j * height + y, color)
                }
            }

            return image
        }

    }

}