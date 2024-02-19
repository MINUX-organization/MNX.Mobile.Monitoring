package com.minux.monitoring.core.network

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.net.Socket

class NetworkScanService(private val context: Context) {
    fun scanNetworkForAggregators(): Flow<List<String>> {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val linkProperties = connectivityManager.getLinkProperties(network)

        return flow {
            linkProperties?.let {
                val aggregators = ArrayList<String>()

                it.linkAddresses.forEach { linkAddress ->
                    if (linkAddress.address is Inet4Address) {
                        linkAddress.address.hostAddress?.let { ip ->
                            val subnetMask = prefixLengthToSubnetMask(linkAddress.prefixLength)
                            aggregators.add("маска подсети: $subnetMask")

                            checkForAggregator(baseIp = ip, netmask = subnetMask) { isAggregator, deviceIP ->
                                if (isAggregator) {
                                    aggregators.add("IP адрес: $deviceIP")
                                } else {
                                    aggregators.add("no aggregator (IP адрес: $deviceIP)")
                                }
                            }
                        }
                    }
                }

                emit(aggregators)
            }
        }
    }

    private fun prefixLengthToSubnetMask(prefixLength: Int): String {
        val mask = (0xffffffff shl (32 - prefixLength)) and 0xffffffffL
        return (mask shr 24 and 0xff).toString() + "." +
                (mask shr 16 and 0xff) + "." +
                (mask shr 8 and 0xff) + "." +
                (mask and 0xff)
    }

    private inline fun checkForAggregator(
        baseIp: String,
        netmask: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val subnet = baseIp.substringBeforeLast(".") + "."
        val startIp = netmask.substringAfterLast(".").toInt() + 1
        val endIp = 255

        for (ip in startIp..endIp) {
            val currentIp = subnet + ip.toString()

            // Проверяем, является ли устройство агрегатором
            if (isPortOpen(currentIp, 80, 200)) { // Порт 80 и таймаут 200 мс для примера
                onResult(true, currentIp)
            } else {
                onResult(false, currentIp)
            }
        }
    }

    private fun isPortOpen(ip: String, port: Int, timeout: Int): Boolean {
        try {
            Socket().use { socket ->
                socket.connect(InetSocketAddress(ip, port), timeout)
                return true
            }
        } catch (e: IOException) {
            // Исключение означает, что порт закрыт или хост недоступен

            return false
        }
    }
}