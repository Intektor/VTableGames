package de.intektor.vtable.common.network.packets.client_to_server

import de.intektor.vtable.common.GameServer
import de.intektor.vtable.common.network.IPacket
import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author Intektor
 */
class RegisterPacketToServer : IPacket {

    lateinit var username: String

    constructor(username: String) {
        this.username = username
    }

    constructor()

    override fun write(output: DataOutputStream) {
        output.writeUTF(username)
    }

    override fun read(input: DataInputStream) {
        username = input.readUTF()
    }

    class Handler : IPacketHandler<RegisterPacketToServer> {

        override fun handlePacket(packet: RegisterPacketToServer, from: ISocket) {
            GameServer.mainThread.addScheduledTask(Runnable {
                GameServer.registerAndInitClient(from, packet)
            })
        }
    }
}