package de.intektor.vtable.common.network

/**
 * @author Intektor
 */
interface IServerSocket {

    fun accept(): ISocket

    fun setSoTimeout(soTimeout: Int)

    fun isClosed(): Boolean

    fun isBound(): Boolean

    fun close()
}