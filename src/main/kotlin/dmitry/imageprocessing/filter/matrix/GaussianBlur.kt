package dmitry.imageprocessing.filter.matrix

import dmitry.imageprocessing.filter.MatrixFilter
import java.awt.image.BufferedImage
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.pow

class GaussianBlur(image: BufferedImage, sigma: Double = 1.0, kernelSideSize: Int = 5):
    MatrixFilter(image, createGaussianKernel(kernelSideSize, sigma))

private fun createGaussianKernel(kernelSideSize: Int, sigma: Double): List<List<Double>> {
    val kernel = MutableList(kernelSideSize) { MutableList(kernelSideSize) { 0.0 } }

    val indent = kernelSideSize / 2


    var sum = 0.0
    for (y in -indent..indent) {
        for (x in -indent..indent) {
            sum += (gaussian(x, y, sigma).also { kernel[x + indent][y + indent] = it })
        }
    }


    for (y in kernel.indices) {
        for (x in kernel[y].indices) {
            kernel[y][x] /= sum
        }
    }

    return kernel
}


private fun gaussian(x: Int, y: Int, sigma: Double) =
    (1 / (2 * PI * sigma.pow(2))) * E.pow( -(x.sqr() + y.sqr()) / ( 2 * sigma.pow(2) ) )

private fun Int.sqr() = this * this
