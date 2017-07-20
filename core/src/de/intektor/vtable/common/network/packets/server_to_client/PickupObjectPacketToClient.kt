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
class PickupObjectPacketToClient : IPacket {

    lateinit var entityUUID: UUID
    lateinit var playerUUID: UUID

    constructor(entityUUID: UUID, playerUUID: UUID) {
        this.entityUUID = entityUUID
        this.playerUUID = playerUUID
    }

    constructor()

    override fun write(output: DataOutputStream) {
        entityUUID.write(output)
        playerUUID.write(output)
    }

    override fun read(input: DataInputStream) {
        entityUUID = readUUID(input)
        playerUUID = readUUID(input)
    }
}