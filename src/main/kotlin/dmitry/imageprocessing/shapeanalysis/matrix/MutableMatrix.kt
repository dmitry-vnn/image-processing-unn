package dmitry.imageprocessing.shapeanalysis.matrix

interface MutableMatrix<T> : Matrix<T> {

    operator fun set(y: Int, x: Int, value: T)

}