package de.intektor.vtable.api.network.tcp

import de.intektor.vtable.common.network.IServerSocket
import java.net.ServerSocket

/**
 * @author Intektor
 */
class TcpServerSocket(port: Int) : IServerSocket {

    private val socket: ServerSocket = ServerSocket(port)

    override fun accept(): TcpSocket = TcpSocket(socket.accept())

    override fun setSoTimeout(soTimeout: Int) {
        socket.soTimeout = soTimeout
    }

    override fun isClosed(): Boolean = socket.isClosed

    override fun isBound(): Boolean = socket.isBound

    override fun close() {
        socket.close()
    }
}