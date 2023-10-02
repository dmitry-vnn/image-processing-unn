package dmitry.example

import java.io.File

object ExamplePathEvaluator {


    inline fun<reified T> evaluatePath(fileName: String): String {
        val projectPath = System.getProperty("user.dir")
        val sep = File.separator
        val filePath = buildString {
            append(projectPath)
            append(sep)
            append("examples")
            append(sep)
            append(T::class.simpleName)
            append(sep)
            append(fileName)
    }

        return filePath

    }

    inline fun<reified T> defaultInputPath(fileType: String = "jpg") =
        evaluatePath<T>("in.$fileType")

    inline fun<reified T> defaultOutputPath(fileType: String = "jpg") =
        evaluatePath<T>("out.$fileType")

}