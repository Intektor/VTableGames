package de.intektor.vtable.common.network.packets.server_to_client

import com.badlogic.gdx.math.Matrix4
import de.intektor.vtable.common.network.IPacket
import de.intektor.vtable.common.network.readMatrix
import de.intektor.vtable.common.network.readUUID
import de.intektor.vtable.common.network.write
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*
import javax.vecmath.Matrix4f

/**
 * @author Intektor
 */
class EntityUpdatePacketToClient : IPacket {

    lateinit var uuid: UUID
    lateinit var transform: Matrix4f

    constructor(uuid: UUID, transform: Matrix4f) {
        this.uuid = uuid
        this.transform = transform
    }

    constructor()

    override fun write(output: DataOutputStream) {
        uuid.write(output)
        transform.write(output)
    }

    override fun read(input: DataInputStream) {
        uuid = readUUID(input)
        transform = readMatrix(input)
    }
}