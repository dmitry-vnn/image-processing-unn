package dmitry.imageprocessing.compression.filters

import dmitry.imageprocessing.compression.FrequencyContainer

class LowerFrequenciesFilter(
    frequencyContainer: FrequencyContainer
): FrequenciesFilter(
    frequencyContainer.imageWidth,
    frequencyContainer.imageHeight,
    frequencyContainer.lowerFrequencies
)