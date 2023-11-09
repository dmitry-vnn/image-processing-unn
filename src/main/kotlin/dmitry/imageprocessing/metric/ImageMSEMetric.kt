package dmitry.imageprocessing.metric

import java.awt.image.BufferedImage
import kotlin.math.pow


class ImageMSEMetric(basicImage: BufferedImage, comparableImage: BufferedImage): AbstractImagesComparator(basicImage, comparableImage) {

    override fun compareGrayMonochromeImages(): Double {
        var result = 0.0

        for (y in 0..<basicMonochromeImage.height) {
            for (x in 0..<basicMonochromeImage.width) {
                result += ((basicMonochromeImage.takeColor(x, y) - comparableMonochromeImage.takeColor(x, y)).toDouble()).pow(2.0)
            }
        }

        result /= basicMonochromeImage.height * basicMonochromeImage.width

        return result
    }



}

