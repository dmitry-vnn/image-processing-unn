package dmitry.noise

import java.awt.image.BufferedImage
import java.lang.StringBuilder
import java.net.URL
import java.net.URLEncoder
import javax.imageio.ImageIO

class ProbabilityCharts(
    private val probability: DoubleArray,
    private val chartLabel: String
) {

    fun create(): BufferedImage {
        val request = createGetHttpRequestUrl(
            "https://quickchart.io/chart",
            mapOf(
                "v" to "2.9.4",
                "c" to """
                   {
                     type: "line",
                     data: {
                       labels: [${probability.indices.joinToString()}],
                       datasets: [
                         {
                           label: "$chartLabel",
                           data: [${probability.joinToString()}],
                           fill: false,
                           borderColor: 'green',
                         },
                       ],
                     },
                   }
                   
               """.trimIndent().replace("\n", "")
            )
        )

        return ImageIO.read(request)
    }


    private fun createGetHttpRequestUrl(path: String, params: Map<String, String> = emptyMap()): URL {

        val builder = StringBuilder(path)

        builder.apply {
            if (params.isNotEmpty()) {
                append("?")

                var isFirstRecord = true

                for ((k, v) in params) {
                    if (!isFirstRecord) {
                        append("&")
                    } else {
                        isFirstRecord = false
                    }

                    append(URLEncoder.encode(k, "UTF-8"))
                    append("=")
                    append(URLEncoder.encode(v, "UTF-8"))
                }
            }
        }

        return URL(builder.toString())
    }

}