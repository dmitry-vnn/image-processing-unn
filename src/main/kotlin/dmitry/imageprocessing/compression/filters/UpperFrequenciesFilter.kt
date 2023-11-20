package dmitry.imageprocessing.compression.filters

import dmitry.imageprocessing.compression.FrequencyContainer

class UpperFrequenciesFilter(
    frequencyContainer: FrequencyContainer
): FrequenciesFilter(
    frequencyContainer.imageWidth,
    frequencyContainer.imageHeight,
    frequencyContainer.upperFrequencies
)