package dmitry.imageprocessing.matrix

interface MutableMatrix<T> : Matrix<T> {

    operator fun set(y: Int, x: Int, value: T)

}