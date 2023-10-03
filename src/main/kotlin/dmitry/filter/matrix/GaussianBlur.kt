package dmitry.filter.matrix

import dmitry.filter.MatrixFilter
import java.awt.image.BufferedImage
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.pow

class GaussianBlur(image: BufferedImage, kernelSideSize: Int = 3):
    MatrixFilter(image, createGaussianKernel(kernelSideSize))

private fun createGaussianKernel(kernelSideSize: Int): List<List<Double>> {
    val kernel = ArrayList<List<Double>>(kernelSideSize)

    for (y in 0..<kernelSideSize) {
        val row = ArrayList<Double>(kernelSideSize)
        kernel.add(row)

        for (x in 0..<kernelSideSize) {
            row.add(gaussian(x, y))
        }
    }

    return kernel

}


private fun gaussian(x: Int, y: Int, sigma: Double = 3.0) =
    (1 / (2 * PI * sigma.pow(2))) * E.pow( -(x.pow(2) * y.pow(2) ) / ( 2 * sigma.pow(2) ) )

private fun Int.pow(i: Int): Int = toDouble().pow(i).toInt()
