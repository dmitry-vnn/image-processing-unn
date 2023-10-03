package dmitry

import java.awt.image.BufferedImage

class ImageRegion(
    val regionStart: Point = Point(0, 0),
    val regionEnd: Point,
    sourceImage: BufferedImage
) {

    val lineHeight get() = IntRange(regionStart.y, regionEnd.y)
    val lineWidth get() = IntRange(regionStart.x, regionEnd.x)

    val width get() = lineWidth.last - lineWidth.first
    val height get() = lineHeight.last - lineHeight.first

    init {
        if (regionStart.x > regionEnd.x || regionStart.y > regionEnd.y) {
            throw IllegalArgumentException("start point mustn't be higher then end point")
        }

        if (lineHeight.first < 0 || lineHeight.last >= sourceImage.height
            || lineWidth.first < 0 || lineWidth.last >= sourceImage.width) {
            throw IllegalArgumentException("region must be inside an image square")
        }
    }

    constructor(image: BufferedImage): this(
        regionEnd = Point(image.width - 1, image.height - 1), sourceImage = image)

    fun square(): Int {
        return width * height
    }


}

