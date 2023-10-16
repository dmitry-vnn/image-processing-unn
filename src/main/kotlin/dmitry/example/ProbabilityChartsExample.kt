package dmitry.example

import dmitry.example.ProbabilityChartsExample.gaussianChartsPath
import dmitry.noise.ProbabilityCharts
import dmitry.noise.additive.GaussianNoiseProbabilityCreator
import dmitry.store.ImageSaver
import kotlin.math.PI
import kotlin.math.sqrt

private object ProbabilityChartsExample {
    val gaussianChartsPath = absolutePath("gaussian-chart", "png")

    fun absolutePath(imageName: String, imageType: String = "png"): String {
        return PathToExampleEvaluator.evaluateImagePath<ProbabilityChartsExample>(imageName, imageType)
    }
}

fun main() {
    val chartsImage = ProbabilityCharts(

        GaussianNoiseProbabilityCreator(
            1 /( 10 * sqrt(2 * PI)), 128.0
        ).createNormalizedGrayscaleProbability(),

        "Gaussian Probability"
    ).create()

    ImageSaver(chartsImage, gaussianChartsPath).save()
}