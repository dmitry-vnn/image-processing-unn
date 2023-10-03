package dmitry.util

import kotlin.math.max
import kotlin.math.min

object ColorExtensions {

    fun bound(color: Int): Int {
        return min(max(color, 0), 255)
    }

    fun bound(color: Double) = bound(color.toInt())

}