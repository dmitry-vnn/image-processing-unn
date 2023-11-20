package dmitry.imageprocessing.util

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.model.PixelColor
import dmitry.imageprocessing.util.ImageExtensions.getPixelColor
import java.awt.image.BufferedImage


object ImageExtensions {

    private fun BufferedImage.map(inPlace: Boolean = true, mapper: (x: Int, y: Int) -> PixelColor): BufferedImage {

        val mappingImage = if (inPlace) this else emptyCopy()

        mappingImage.apply {
            forEach { x, y ->
                setRGB(x, y, mapper(x, y).rgb)
            }

            return this
        }
    }

    fun BufferedImage.mapToNew(mapper: (x: Int, y: Int) -> PixelColor) =
        map(false, mapper)

    fun BufferedImage.mapInPlace(mapper: (x: Int, y: Int) -> PixelColor) =
        map(true, mapper)

    fun BufferedImage.forEach(action: (x: Int, y: Int) -> Unit) {
        for (y in 0..<height) {
            for (x in 0..<width) {
                action(x, y)
            }
        }
    }

    operator fun BufferedImage.contains(point: Point) =
        point.x in 0..<width &&
        point.y in 0..<height

    fun BufferedImage.emptyCopy() =
        BufferedImage(width, height, type)

    fun BufferedImage.copy(): BufferedImage {
        val cm = colorModel
        val isAlphaPremultiplied = cm.isAlphaPremultiplied
        val raster = copyData(null)
        return BufferedImage(cm, raster, isAlphaPremultiplied, null)
    }

    fun BufferedImage.getPixelColor(x: Int, y: Int): PixelColor {
        return PixelColor.Factory.fromRGB(getRGB(x, y))
    }

    operator fun BufferedImage.iterator(): Iterator<PixelColor> {
        return BufferedImageIterator(this)
    }

}

private class BufferedImageIterator(
    val image: BufferedImage
) : Iterator<PixelColor> {

    var x = 0
    var y = 0

    override fun hasNext(): Boolean {
        return !(x == 0 && y == image.height)
    }

    override fun next(): PixelColor {
        if (!hasNext()) {
            throw NoSuchElementException()
        }

        val currentPixel = image.getPixelColor(x, y)

        x++

        if (x >= image.width) {
            x = 0
            y++
        }

        return currentPixel
    }

}
