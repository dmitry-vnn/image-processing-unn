package dmitry.imageprocessing.model

import java.awt.Color
import kotlin.math.max
import kotlin.math.min

class PixelColor private constructor(red: Int, green: Int, blue: Int)  {

    var red: Int = colorInBoundOrError(red)
        set(value) {
            field = colorInBoundOrError(value)
        }

    var green: Int = colorInBoundOrError(green)
        set(value) {
            field = colorInBoundOrError(value)
        }

    var blue: Int = colorInBoundOrError(blue)
        set(value) {
            field = colorInBoundOrError(value)
        }


    val awtColor get() = Color(red, green, blue)

    val grayscale get() = (red + green + blue) / 3

    val rgb get() = awtColor.rgb

    private fun colorInBoundOrError(value: Int): Int {
        if (value !in 0..255) {
            throw IllegalArgumentException()
        }
        return value
    }

    companion object Factory {

        fun fromRGB(rgb: Int): PixelColor {
            val color = Color(rgb)
            return PixelColor(
                color.red,
                color.green,
                color.blue
            )
        }

        fun fromRGB(r: Int, g: Int, b: Int) =
            PixelColor(r, g, b)

        fun fromAwtColor(color: Color) =
            fromRGB(color.red, color.green, color.blue)

        fun fromGrayscale(gray: Int) =
            fromRGB(gray, gray, gray)

        fun fromBoundedRGB(r: Number, g: Number, b: Number) =
            fromRGB(r.bound(), g.bound(), b.bound())

        fun fromBoundedGrayscale(gray: Number) =
            fromGrayscale(gray.bound())

    }

}

private fun Number.bound(): Int {
    return max(0, min(toInt(), 255))
}


