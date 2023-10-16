package dmitry.noise

interface GrayscaleProbabilityCreator {

    fun createGrayscaleProbability(): DoubleArray

    fun createNormalizedGrayscaleProbability() =
        createGrayscaleProbability().apply {
            val sum = sum()

            for (i in indices) {
                this[i] /= sum
            }
        }


}
