package dmitry.imageprocessing.store

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ImageLoader {

    fun load(path: String): BufferedImage {
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return ImageIO.read(file)
    }


}