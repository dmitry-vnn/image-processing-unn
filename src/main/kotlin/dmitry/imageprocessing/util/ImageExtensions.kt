package dmitry.imageprocessing.util

import dmitry.imageprocessing.Point
import dmitry.imageprocessing.model.PixelColor
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
        for (x in 0..<width) {
            for (y in 0..<height) {
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

}