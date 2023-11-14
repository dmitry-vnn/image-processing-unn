package dmitry.example

import dmitry.example.OpenCVSample.inImagePath
import dmitry.example.OpenCVSample.outImagePath
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

private object OpenCVSample {

    val inImagePath = PathToExampleEvaluator.evaluateImagePath<OpenCVSample>("in", "png")
    val outImagePath = PathToExampleEvaluator.evaluateImagePath<OpenCVSample>("out", "png")
}

fun main() {

    loadOpenCV()

    val binaryImage = Imgcodecs.imread(inImagePath, Imgcodecs.IMREAD_GRAYSCALE)

    val resultImage = binaryImage.clone()
    Imgproc.cvtColor(resultImage, resultImage, Imgproc.COLOR_GRAY2BGR)


    val labels = Mat()
    val stats = Mat()
    val centroids = Mat()

    Imgproc.connectedComponentsWithStats(binaryImage, labels, stats, centroids)

    val markedImage = Array(binaryImage.height()) { IntArray(binaryImage.width()) }

    for (y in 0..<labels.height()) {
        for (x in 0..<labels.width()) {
            markedImage[y][x] = labels.get(y, x)[0].toInt()
        }
    }

    for (i in 1..<stats.rows()) {

        //Shape positions
        val left = stats.get(i, Imgproc.CC_STAT_LEFT)[0]
        val top = stats.get(i, Imgproc.CC_STAT_LEFT)[0]
        val width = stats.get(i, Imgproc.CC_STAT_WIDTH)[0]
        val height = stats.get(i, Imgproc.CC_STAT_HEIGHT)[0]

        // Draw a bounding box around the component
        Imgproc.rectangle(resultImage, Point(left, top), Point(left + width, top + height),
            Scalar(0.0, 255.0, 0.0), 1
        )

        val cx = centroids[i, 0][0]
        val cy = centroids[i, 1][0]

        println("Component $i: Centroid - ($cx, $cy)");


    }

    Imgcodecs.imwrite(outImagePath, resultImage)


}

fun loadOpenCV() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
}
