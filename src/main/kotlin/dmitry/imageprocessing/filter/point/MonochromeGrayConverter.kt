package dmitry.imageprocessing.filter.point

import dmitry.imageprocessing.ImageRegion
import dmitry.imageprocessing.filter.PointFilter
import java.awt.Color
import java.awt.image.BufferedImage

class MonochromeGrayConverter(
    image: BufferedImage,
    imageRegion: ImageRegion = ImageRegion(image)
): PointFilter(image, imageRegion) {

    override fun mapColor(color: Color): Color {
        val monochromeColor = color.run { red + green + blue } / 3
        return Color(monochromeColor, monochromeColor, monochromeColor)
    }

}