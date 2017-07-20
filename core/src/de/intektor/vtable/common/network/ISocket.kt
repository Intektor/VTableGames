package de.intektor.vtable.common.network

import java.io.InputStream
import java.io.OutputStream

/**
 * @author Intektor
 */
interface ISocket {

    fun getInputStream(): InputStream

    fun getOutputStream(): OutputStream

    fun isConnected(): Boolean

    fun close()
}