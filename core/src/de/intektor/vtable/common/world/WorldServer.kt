package de.intektor.vtable.common.world

import com.bulletphysics.linearmath.Transform
import de.intektor.vtable.common.GameServer
import de.intektor.vtable.common.network.packets.server_to_client.EntityUpdatePacketToClient
import de.intektor.vtable.common.network.packets.server_to_client.SpawnEntityPacketToClient
import de.intektor.vtable.common.world.entity.Entity
import de.intektor.vtable.common.world.entity.EntityGround
import de.intektor.vtable.common.world.entity.schafkopfn.EntityCard
import de.intektor.vtable.common.world.entity.test.EntityTestCube
import javax.vecmath.Matrix4f

/**
 * @author Intektor
 */
class WorldServer(val server: GameServer) : World(false) {

    private var lastUpdateTick: Int = 0

    init {
        spawnEntityInWorld(EntityGround(this))
        spawnEntityInWorld(EntityCard(0f, 10f, 0f, this))
        spawnEntityInWorld(EntityTestCube(0f, 15f, 0f, this))
    }

    override fun spawnEntityInWorld(entity: Entity) {
        super.spawnEntityInWorld(entity)
        server.broadcast(SpawnEntityPacketToClient(entity))
    }

    override fun updateWorld() {
        super.updateWorld()
        if (currentTickCount - lastUpdateTick >= 10) {
            loadedEntityList
                    .filter { it.shouldSendWorldTransformUpdates }
                    .forEach { server.broadcast(EntityUpdatePacketToClient(it.uuid, it.body.getWorldTransform(Transform()).getMatrix(Matrix4f()))) }
            lastUpdateTick = currentTickCount
        }
    }
}