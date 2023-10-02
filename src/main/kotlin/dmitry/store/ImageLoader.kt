package dmitry.store

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageLoader(private val path: String) {

    fun load(): BufferedImage = ImageIO.read(File(path))

}