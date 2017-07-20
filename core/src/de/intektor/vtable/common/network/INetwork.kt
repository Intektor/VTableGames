package de.intektor.vtable.common.network

/**
 * @author Intektor
 */
interface INetwork {

    fun buildSocket(ip: String, port: Int, side: NetworkType): ISocket

    fun buildServerSocket(port: Int, type: NetworkType): IServerSocket
}
