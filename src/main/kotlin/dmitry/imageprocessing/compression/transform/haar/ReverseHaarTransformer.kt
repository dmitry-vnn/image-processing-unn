package dmitry.imageprocessing.compression.transform.haar

import java.awt.image.BufferedImage

interface ReverseHaarTransformer {

    fun createImageOnFrequencies(): BufferedImage

}