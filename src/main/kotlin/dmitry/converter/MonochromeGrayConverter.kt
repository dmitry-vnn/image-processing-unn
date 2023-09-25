package dmitry.converter

import java.awt.Color
import java.awt.image.BufferedImage

class MonochromeGrayConverter(image: BufferedImage) : PointFilter(image) {

    override fun getColorFrom(color: Color): Color {
        val monochromeColor = color.run { red + green + blue } / 3
        return Color(monochromeColor, monochromeColor, monochromeColor)
    }

}