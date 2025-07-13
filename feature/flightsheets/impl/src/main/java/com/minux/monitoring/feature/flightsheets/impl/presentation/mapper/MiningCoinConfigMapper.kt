package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.cryptos.api.model.PoolDto
import com.minux.monitoring.feature.cryptos.api.model.WalletDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.mining.MiningCoinConfigDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.mining.MiningCoinConfigInputDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.mining.MiningPoolDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.mining.MiningWalletDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel

internal fun MiningCoinConfigDto.toMiningCoinConfigModel(): MiningCoinConfigModel {
    return MiningCoinConfigModel(
        pool = pool?.toMiningPoolModel(),
        wallet = wallet?.toMiningWalletModel(),
        poolPassword = poolPassword
    )
}

internal fun MiningCoinConfigDto.toMiningCoinConfigInputModel(
    pools: List<MiningPoolModel>?,
    wallets: List<MiningWalletModel>?
): MiningCoinConfigInputModel {
    val selectedPool = pool?.toMiningPoolModel() ?: pools?.firstOrNull()
    val selectedWallet = wallet?.toMiningWalletModel() ?: wallets?.firstOrNull()

    return MiningCoinConfigInputModel(
        selectedPool = selectedPool,
        isPoolValid = selectedPool != null,
        selectedWallet = selectedWallet,
        isWalletValid = selectedWallet != null,
        poolPassword = poolPassword
    )
}

internal fun MiningCoinConfigInputModel.toMiningCoinConfigInputDto(): MiningCoinConfigInputDto {
    return MiningCoinConfigInputDto(
        poolId = selectedPool!!.id,
        walletId = selectedWallet!!.id,
        poolPassword = poolPassword
    )
}

internal fun PoolDto.toMiningPoolModel(): MiningPoolModel {
    return MiningPoolModel(
        id = id,
        name = domain ?: "",
        cryptocurrency = cryptocurrency ?: ""
    )
}

internal fun WalletDto.toMiningWalletModel(): MiningWalletModel {
    return MiningWalletModel(
        id = id,
        name = name ?: "",
        coin = cryptocurrency ?: ""
    )
}

private fun MiningPoolDto.toMiningPoolModel(): MiningPoolModel {
    return MiningPoolModel(
        id = id,
        name = domain ?: "",
        cryptocurrency = cryptocurrency ?: ""
    )
}

private fun MiningWalletDto.toMiningWalletModel(): MiningWalletModel {
    return MiningWalletModel(
        id = id,
        name = name ?: "",
        coin = cryptocurrency ?: ""
    )
}