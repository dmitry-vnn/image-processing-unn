package dmitry.imageprocessing.noise

import java.awt.image.BufferedImage

interface NoiseGenerator {

    fun generate(): BufferedImage

}