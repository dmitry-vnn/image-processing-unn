package dmitry.imageprocessing.compression

class FrequencyContainer(
    val imageHeight: Int,
    val imageWidth: Int,
    val lowerFrequencies: List<Double>,
    val upperFrequencies: List<Double>,
)
