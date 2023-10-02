package dmitry.store

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageSaver(private val image: BufferedImage, private val path: String) {

    fun save() {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        ImageIO.write(image, path.split('.').lastOrNull() ?: "jpg", file)
    }

}
