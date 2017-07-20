package de.intektor.vtable.common.network.packets.server_to_client

import com.badlogic.gdx.math.Matrix4
import com.bulletphysics.linearmath.Transform
import de.intektor.vtable.common.network.*
import de.intektor.vtable.common.world.entity.Entity
import de.intektor.vtable.common.world.entity.EntityRegistry
import java.io.DataInputStream
import java.io.DataOutputStream
import javax.vecmath.Matrix4f

/**
 * @author Intektor
 */
class SpawnEntityPacketToClient : IPacket {

    lateinit var entity: Entity

    constructor(entity: Entity) {
        this.entity = entity
    }

    constructor()

    override fun write(output: DataOutputStream) {
        output.writeInt(EntityRegistry.getID(entity.javaClass))
        entity.uuid.write(output)
        val m = Matrix4f()
        entity.motionState.getWorldTransform(Transform()).getMatrix(m)
        m.write(output)
        entity.writeSpawnData(output)

    }

    override fun read(input: DataInputStream) {
        entity = EntityRegistry.create(input.readInt(), readUUID(input))
        entity.motionState.setWorldTransform(Transform(readMatrix(input)))
        entity.readSpawnData(input)
    }
}