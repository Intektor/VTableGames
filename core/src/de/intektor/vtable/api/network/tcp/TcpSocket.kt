package de.intektor.vtable.api.network.tcp

import de.intektor.vtable.common.network.ISocket
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

/**
 * @author Intektor
 */
class TcpSocket : ISocket {

    val socket: Socket

    constructor(ip: String, port: Int) {
        this.socket = Socket(ip, port)
    }

    constructor(socket: Socket) {
        this.socket = socket
    }

    override fun getInputStream(): InputStream = socket.getInputStream()

    override fun getOutputStream(): OutputStream = socket.getOutputStream()

    override fun isConnected(): Boolean = socket.isConnected

    override fun close() {
        socket.close()
    }
}