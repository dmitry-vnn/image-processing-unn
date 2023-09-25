package dmitry.converter

import java.awt.Color
import java.awt.image.BufferedImage

abstract class PointFilter(protected val image: BufferedImage): ImageConverter {

    protected abstract fun getColorFrom(color: Color): Color

    override fun convert(): BufferedImage {
        for (y in 0..<image.height) {
            for (x in 0..<image.width) {
                image.setRGB(x, y, getColorFrom(Color(image.getRGB(x, y))).rgb)
            }
        }

        return image
    }
}