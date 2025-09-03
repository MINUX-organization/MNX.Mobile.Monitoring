package com.minux.monitoring.feature.flightsheets.impl.data.model.device

import com.minux.monitoring.feature.flightsheets.impl.data.model.mining.MiningCoinConfigInputDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface DeviceMiningConfigInputDto {

    val coinConfigs: List<MiningCoinConfigInputDto?>
    val additionalArguments: String?
    val configFileContent: String?

    @Serializable
    @SerialName("CPU")
    class CpuMiningConfigInputDto(
        override val coinConfigs: List<MiningCoinConfigInputDto?>,
        override val additionalArguments: String? = null,
        override val configFileContent: String? = null,
        val hugePages: Int? = null,
        val threadsCount: Int? = null
    ) : DeviceMiningConfigInputDto

    @Serializable
    @SerialName("GPU")
    class GpuMiningConfigInputDto(
        override val coinConfigs: List<MiningCoinConfigInputDto?>,
        override val additionalArguments: String? = null,
        override val configFileContent: String? = null
    ) : DeviceMiningConfigInputDto
}