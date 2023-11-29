package dmitry.imageprocessing.wavelet

import dmitry.imageprocessing.matrix.Matrix
import dmitry.imageprocessing.util.ImageExtensions.BufferedImage
import dmitry.imageprocessing.util.ImageExtensions.forEachIndexed
import java.awt.image.BufferedImage

class ImageMatrixJoiner(
    private val matrixOfImages: Matrix<BufferedImage>
) {
    
    fun join(): BufferedImage {
        val partWidth = matrixOfImages[0, 0].width
        val partHeight = matrixOfImages[0, 0].height
        val image = BufferedImage(
            matrixOfImages.width * partWidth,
            matrixOfImages.height * partHeight,
        )

        matrixOfImages.forEachIndexed { x, y, part ->
            part.forEachIndexed{ px, py, color ->
                image.setRGB(x * partWidth + px, y * partHeight + py, color.rgb)
            }
        }

        return image

    }

}