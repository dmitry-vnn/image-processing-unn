package dmitry.filter

import dmitry.ImageRegion
import java.awt.Color
import java.awt.image.BufferedImage

abstract class PointFilter(
    protected val image: BufferedImage,
    private val region: ImageRegion = ImageRegion(image)
): ImageConverter {

    protected abstract fun mapColor(color: Color): Color

    override fun convert(): BufferedImage {
        for (y in region.lineHeight) {
            for (x in region.lineWidth) {
                image.setRGB(x, y, mapColor(Color(image.getRGB(x, y))).rgb)
            }
        }

        return image
    }
}