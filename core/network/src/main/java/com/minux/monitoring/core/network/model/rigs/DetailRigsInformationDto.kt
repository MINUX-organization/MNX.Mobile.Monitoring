package com.minux.monitoring.core.network.model.rigs

import com.minux.monitoring.core.network.model.GpusCountDto
import com.minux.monitoring.core.network.model.ValueUnitDto

data class DetailRigsInformationDto(
    val id: String,
    val index: Int,
    val name: String,
    val isActive: Boolean,
    val onlineState: String,
    val power: ValueUnitDto,
    val gpusCount: GpusCountDto,
    val cpusCount: Int,
    val hddsCount: Int,
    val flightSheets: Array<String>,
    val amdVersion: String,
    val nvidiaVersion: String,
    val openClVersion: String,
    val cudaVersion: String,
    val linuxVersion: String,
    val minuxVersion: String,
    val mac: String,
    val localIp: String,
    val globalIp: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetailRigsInformationDto

        return flightSheets.contentEquals(other.flightSheets)
    }

    override fun hashCode(): Int {
        return flightSheets.contentHashCode()
    }
}
