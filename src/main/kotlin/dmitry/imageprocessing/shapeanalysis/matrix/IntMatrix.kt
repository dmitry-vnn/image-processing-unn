package dmitry.imageprocessing.shapeanalysis.matrix

class IntMatrix(
    override val height: Int,
    override val width: Int
): MutableMatrix<Int> {

    val matrix = Array(height) { IntArray(width) }

    private val xRange = 0..<width
    private val yRange = 0..<height

    override fun set(y: Int, x: Int, value: Int) {
        validatePoint(x, y)
        matrix[y][x] = value
    }

    private fun validatePoint(x: Int, y: Int) {
        if (x !in xRange) {
            throw IndexOutOfBoundsException("x = $x out of $xRange")
        }

        if (y !in yRange) {
            throw IndexOutOfBoundsException("y = $y out of $yRange")
        }

    }

    override fun get(y: Int, x: Int): Int {
        validatePoint(x, y)
        return matrix[y][x]
    }

    override fun forEachIndexed(action: (x: Int, y: Int, element: Int) -> Unit) {
        for (y in yRange) {
            for (x in xRange) {
                action(x, y, matrix[y][x])
            }
        }
    }

    override fun toString(): String {
        return StringBuilder().apply {
            for (y in yRange) {
                for (x in xRange) {
                    append(matrix[y][x])
                    if (x < xRange.last) {
                        append(", ")
                    }
                }
                append('\n')
            }
        }.toString()
    }

}