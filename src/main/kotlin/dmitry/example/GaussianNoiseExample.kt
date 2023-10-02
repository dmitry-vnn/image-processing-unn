package dmitry.example

import dmitry.noise.additive.GaussianNoiseGenerator
import dmitry.store.ImageLoader
import dmitry.store.ImageSaver
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class GaussianNoiseExample

val inImagePath = ExamplePathEvaluator.defaultInputPath<GaussianNoiseExample>()
val outImagePath = ExamplePathEvaluator.defaultOutputPath<GaussianNoiseExample>()

fun main() {

    val generatedImage = GaussianNoiseGenerator(10.0, 20.0, .7, ImageLoader(inImagePath).load()).generate()
    ImageSaver(generatedImage, outImagePath).save()

}
