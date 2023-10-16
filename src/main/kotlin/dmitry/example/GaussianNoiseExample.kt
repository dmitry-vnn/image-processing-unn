package dmitry.example

import dmitry.example.GaussianNoiseExample.inImagePath
import dmitry.example.GaussianNoiseExample.outImagePath
import dmitry.noise.additive.GaussianNoiseGenerator
import dmitry.store.ImageLoader
import dmitry.store.ImageSaver
import kotlin.math.*

private object GaussianNoiseExample {
    val inImagePath = PathToExampleEvaluator.defaultInputPath<GaussianNoiseExample>()
    val outImagePath = PathToExampleEvaluator.defaultOutputPath<GaussianNoiseExample>()
}

fun main() {

    val generatedImage = GaussianNoiseGenerator(
        1 /( 10 * sqrt(2 * PI) ),
        128.0,
        .25,
        ImageLoader.load(inImagePath)
    ).generate()

    ImageSaver(generatedImage, outImagePath).save()

}
