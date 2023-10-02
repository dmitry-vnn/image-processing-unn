package dmitry.example

import java.io.File

object PathToExampleEvaluator {


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

    inline fun<reified T> evaluateImagePath(fileName: String, fileType: String = "jpg") =
        evaluatePath<T>("$fileName.$fileType")

    inline fun<reified T> defaultInputPath() =
        evaluateImagePath<T>("in")

    inline fun<reified T> defaultOutputPath() =
        evaluateImagePath<T>("out")

}