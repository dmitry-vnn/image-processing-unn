package dmitry.imageprocessing.compression.transform.haar

import dmitry.imageprocessing.compression.FrequencyContainer

interface ForwardHaarTransformer {

    fun groupByFrequencies(): FrequencyContainer

}