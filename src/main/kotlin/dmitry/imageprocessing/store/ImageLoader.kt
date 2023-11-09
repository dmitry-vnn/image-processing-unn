package dmitry.imageprocessing.store

import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

object ImageLoader {

    fun load(path: String): BufferedImage = ImageIO.read(File(path))


}