package com.minux.monitoring.feature.flightsheets.impl.data.model.device

import com.minux.monitoring.feature.flightsheets.impl.data.model.mining.MiningCoinConfigDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface DeviceMiningConfigDto {

    val coinConfigs: List<MiningCoinConfigDto?>
    val additionalArguments: String?
    val configFileContent: String?

    @Serializable
    @SerialName("CPU")
    class CpuMiningConfigDto(
        override val coinConfigs: List<MiningCoinConfigDto?>,
        override val additionalArguments: String? = null,
        override val configFileContent: String? = null,
        val hugePages: Int? = null,
        val threadsCount: Int? = null
    ) : DeviceMiningConfigDto

    @Serializable
    @SerialName("GPU")
    class GpuMiningConfigDto(
        override val coinConfigs: List<MiningCoinConfigDto?>,
        override val additionalArguments: String? = null,
        override val configFileContent: String? = null
    ) : DeviceMiningConfigDto
}