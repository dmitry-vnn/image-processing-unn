package dmitry.imageprocessing

class Point(x: Int, y: Int) {
    private val pair = Pair(x, y)

    val x get() = pair.first
    val y get() = pair.second

    operator fun component1() = x

    operator fun component2() = y

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point) return false

        if (pair != other.pair) return false

        return true
    }

    override fun hashCode(): Int {
        return pair.hashCode()
    }


}
