package dmitry.imageprocessing.filter

import java.awt.image.BufferedImage

interface ImageConverter {

    fun convert(): BufferedImage

}