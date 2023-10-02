package dmitry

import java.awt.image.BufferedImage

class ImageRegion(
    val regionStart: Point = Point(0, 0),
    val regionEnd: Point) {

    val lineHeight get() = IntRange(regionStart.y, regionEnd.y)
    val lineWidth get() = IntRange(regionStart.x, regionEnd.x)

    init {
        if (regionStart.x > regionEnd.x || regionStart.y > regionEnd.y) {
            throw IllegalArgumentException("regionEnd must be >= regionStart")
        }
    }

    constructor(image: BufferedImage): this(regionEnd = Point(image.width - 1, image.height - 1))



    fun square(): Int {
        return (lineHeight.last - lineHeight.first) * (lineWidth.last - lineWidth.first)
    }
}

