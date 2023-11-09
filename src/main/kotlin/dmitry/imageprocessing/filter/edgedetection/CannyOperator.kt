package dmitry.imageprocessing.filter.edgedetection

import dmitry.imageprocessing.filter.ImageConverter
import jcanny.JCanny
import java.awt.image.BufferedImage

class CannyOperator(
    private val image: BufferedImage
): ImageConverter {

    private val cannyThresholdRatio = .2 //Suggested range .2 - .4
    private val cannyStdDev = 1 //Range 1-3


    override fun convert(): BufferedImage {
        return JCanny.CannyEdges(image, cannyStdDev, cannyThresholdRatio)
    }
}