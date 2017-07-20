package de.intektor.vtable.common.network.packets.server_to_client

import de.intektor.vtable.common.network.IPacket
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author Intektor
 */
class KickPacketToClient : IPacket {

    lateinit var message: String

    constructor(message: String) {
        this.message = message
    }

    constructor()

    override fun write(output: DataOutputStream) {
        output.writeUTF(message)
    }


    override fun read(input: DataInputStream) {
        message = input.readUTF()
    }
}