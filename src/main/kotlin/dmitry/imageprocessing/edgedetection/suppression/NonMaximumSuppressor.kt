package dmitry.imageprocessing.edgedetection.suppression

import java.awt.image.BufferedImage

interface NonMaximumSuppressor {

    fun suppressNonMaximum(): BufferedImage

}