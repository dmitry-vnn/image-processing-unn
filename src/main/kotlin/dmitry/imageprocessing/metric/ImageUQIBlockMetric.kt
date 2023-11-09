package dmitry.imageprocessing.metric

import dmitry.imageprocessing.ImageRegion
import dmitry.imageprocessing.Point
import java.awt.image.BufferedImage
import kotlin.math.ceil
import kotlin.math.min

class ImageUQIBlockMetric(
    private val basicImage: BufferedImage,
    private val comparableImage: BufferedImage,
    private val blockSize: Int
): ImageMetric {

    override fun calculate(): Double {
        val blocksOnWidthLine = ceil(basicImage.width.toDouble() / blockSize).toInt()
        val blocksOnHeightLine = ceil(basicImage.height.toDouble() / blockSize).toInt()

        var result = 0.0

        for (y in 0..<blocksOnHeightLine)
            for (x in 0..<blocksOnWidthLine) {
                val p1 = Point(x * blockSize, y * blockSize)
                val p2 = Point(
                    min(p1.x + blockSize, basicImage.width - 1),
                    min(p1.y + blockSize, basicImage.height - 1)
                )

                val region = ImageRegion(p1, p2, basicImage)

                result += ImageUQIMetric(basicImage, comparableImage, region).calculate()

            }

        result /= blocksOnWidthLine * blocksOnWidthLine

        return result
    }

}

fun main() {
    println(ceil(1.2))
    println(ceil(1.56))
    println(ceil(1.0))
    println(ceil(1.99999))
}