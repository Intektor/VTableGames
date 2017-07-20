package de.intektor.vtable.common.network.packets.server_to_client

import de.intektor.vtable.common.network.IPacket
import de.intektor.vtable.common.network.readUUID
import de.intektor.vtable.common.network.write
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*

/**
 * @author Intektor
 */
class DropObjectPacketToClient : IPacket {

    lateinit var entityUUID: UUID

    constructor(entityUUID: UUID) {
        this.entityUUID = entityUUID
    }

    constructor()

    override fun write(output: DataOutputStream) {
        entityUUID.write(output)
    }

    override fun read(input: DataInputStream) {
        entityUUID = readUUID(input)
    }
}