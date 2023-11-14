package dmitry.imageprocessing.shapeanalysis.matrix

interface Matrix<T> {

    val height: Int
    val width: Int

    operator fun get(y: Int, x: Int): T

    fun forEachIndexed(action: (x: Int, y: Int, element: T) -> Unit)
    fun forEach(action: (element: T) -> Unit) {
        forEachIndexed { _, _, element -> action(element) }
    }

}