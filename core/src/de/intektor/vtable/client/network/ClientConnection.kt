package de.intektor.vtable.client.network

import de.intektor.vtable.GameClient
import de.intektor.vtable.common.CommonCode
import de.intektor.vtable.common.network.*
import de.intektor.vtable.common.network.packets.client_to_server.RegisterPacketToServer
import java.io.DataInputStream

/**
 * @author Intektor
 */
object ClientConnection {

    private @Volatile var socket: ISocket? = null

    val isConnected: Boolean
        get() = socket != null && socket!!.isConnected()

    fun connect(ip: String, port: Int) {
        object : Thread() {
            override fun run() {
                socket = GameClient.network.buildSocket(ip, port, NetworkType.TCP)

                RegisterPacketToServer("Intektor").sendTo(socket!!, CommonCode.packetRegistry)

                val inputStream = DataInputStream(socket!!.getInputStream())
                while (isConnected) {
                    readAndHandlePacket(inputStream, CommonCode.packetRegistry, NetworkSide.CLIENT, socket!!)
                }
            }
        }.start()
    }

    fun sendPacketToServer(packet: IPacket) {
        packet.sendTo(socket!!, CommonCode.packetRegistry)
    }

    fun disconnect() {
        if (socket != null) {
            socket!!.close()
            socket = null
        }
    }
}