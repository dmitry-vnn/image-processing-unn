package dmitry.imageprocessing.edgedetection.tracking

import java.awt.image.BufferedImage

interface EdgeTracker {

    fun track(): BufferedImage

}