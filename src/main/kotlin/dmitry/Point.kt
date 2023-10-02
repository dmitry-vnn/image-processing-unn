package dmitry

class Point(x: Int, y: Int) {
    private val pair = Pair(x, y)

    val x get() = pair.first
    val y get() = pair.second
}