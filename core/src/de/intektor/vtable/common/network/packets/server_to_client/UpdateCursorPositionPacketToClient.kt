package de.intektor.vtable.common.network.packets.server_to_client

import de.intektor.vtable.common.network.IPacket
import de.intektor.vtable.common.network.readUUID
import de.intektor.vtable.common.network.readVector3f
import de.intektor.vtable.common.network.write
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
class UpdateCursorPositionPacketToClient : IPacket {

    lateinit var playerUUID: UUID
    lateinit var position: Vector3f

    constructor(playerUUID: UUID, position: Vector3f) {
        this.playerUUID = playerUUID
        this.position = position
    }

    constructor()

    override fun write(output: DataOutputStream) {
        playerUUID.write(output)
        position.write(output)
    }

    override fun read(input: DataInputStream) {
        playerUUID = readUUID(input)
        position = readVector3f(input)
    }

}