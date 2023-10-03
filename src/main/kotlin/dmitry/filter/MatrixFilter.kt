package dmitry.filter

import dmitry.ImageRegion
import dmitry.util.ColorExtensions.bound
import java.awt.Color
import java.awt.image.BufferedImage

abstract class MatrixFilter(
    protected val image: BufferedImage,
    protected val kernelMatrix: List<List<Double>>,
    protected val region: ImageRegion = ImageRegion(image)
): ImageConverter {

    init {
        if (!(kernelMatrix.isNotEmpty() && kernelMatrix[0].size == kernelMatrix.size && kernelMatrix.size % 2 == 1)) {
            throw IllegalArgumentException("kernel matrix must be are not-empty, odd and square")
        }

        if (region.width < kernelMatrix.size || region.height < kernelMatrix.size) {
            throw IllegalArgumentException("kernel matrix size must be less than image region size")
        }

    }

    override fun convert(): BufferedImage {

        val kernelSideSize = kernelMatrix.size
        val indent = kernelSideSize / 2

        for (x in indent..<region.width - indent)
            for (y in indent..<region.height - indent) {

                var (r, g, b) = Triple(0.0, 0.0, 0.0)

                for (j in kernelMatrix.indices)
                    for (i in kernelMatrix[j].indices) {

                        val colorOfConvolutionPixel = Color(image.getRGB(x - (indent - i), y - (indent - j)))
                        val multiplier = kernelMatrix[j][i]

                        r += colorOfConvolutionPixel.red * multiplier
                        g += colorOfConvolutionPixel.green * multiplier
                        b += colorOfConvolutionPixel.blue * multiplier

                    }

                val resultColor = Color(bound(r), bound(g), bound(b))
                image.setRGB(x, y, resultColor.rgb)
            }


        return image
    }
}