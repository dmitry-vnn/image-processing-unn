package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.matrix.Matrix
import java.awt.image.BufferedImage

class ImageSplitter(
    private val joinedImage: BufferedImage
) {

    fun split(): Matrix<BufferedImage> {
        val partWidth = joinedImage.width / 2
        val partHeight = joinedImage.height / 2

        val first = joinedImage.getSubimage(0, 0, partWidth, partHeight)
        val second = joinedImage.getSubimage(partWidth, 0, partWidth, partHeight)
        val third = joinedImage.getSubimage(0, partHeight, partWidth, partHeight)
        val fourth = joinedImage.getSubimage(partWidth, partHeight, partWidth, partHeight)

        return ForwardTransformationImageMatrix(
            first, second, third, fourth
        )

    }

}
