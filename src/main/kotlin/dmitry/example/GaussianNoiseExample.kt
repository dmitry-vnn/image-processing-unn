package dmitry.example

import dmitry.example.GaussianNoiseExample.inImagePath
import dmitry.example.GaussianNoiseExample.outImagePath
import dmitry.noise.additive.GaussianNoiseGenerator
import dmitry.store.ImageLoader
import dmitry.store.ImageSaver

private object GaussianNoiseExample {
    val inImagePath = PathToExampleEvaluator.defaultInputPath<GaussianNoiseExample>()
    val outImagePath = PathToExampleEvaluator.defaultOutputPath<GaussianNoiseExample>()
}

fun main() {

    val generatedImage = GaussianNoiseGenerator(10.0, 20.0, .7, ImageLoader.load(inImagePath)).generate()
    ImageSaver(generatedImage, outImagePath).save()

}
