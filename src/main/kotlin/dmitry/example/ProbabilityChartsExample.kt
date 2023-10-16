package dmitry.example

import dmitry.example.TestCharts.gaussianChartsPath
import dmitry.noise.ProbabilityCharts
import dmitry.noise.additive.GaussianNoiseProbabilityCreator
import dmitry.store.ImageSaver

private object TestCharts {
    val gaussianChartsPath = absolutePath("gaussian_charts", "png")

    fun absolutePath(imageName: String, imageType: String = "png"): String {
        return PathToExampleEvaluator.evaluateImagePath<TestCharts>(imageName, imageType)
    }
}

fun main() {
    val chartsImage = ProbabilityCharts(
        GaussianNoiseProbabilityCreator.createGrayscaleProbability(10.0, 20.0),
        "Gaussian Probability"
    ).create()

    ImageSaver(chartsImage, gaussianChartsPath).save()
}