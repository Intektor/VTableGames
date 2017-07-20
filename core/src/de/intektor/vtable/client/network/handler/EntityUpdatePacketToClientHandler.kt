package de.intektor.vtable.client.network.handler

import com.bulletphysics.linearmath.Transform
import de.intektor.vtable.GameClient
import de.intektor.vtable.client.guis.GuiPlay
import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import de.intektor.vtable.common.network.packets.server_to_client.EntityUpdatePacketToClient

/**
 * @author Intektor
 */
class EntityUpdatePacketToClientHandler : IPacketHandler<EntityUpdatePacketToClient> {

    override fun handlePacket(packet: EntityUpdatePacketToClient, from: ISocket) {
        GameClient.addScheduledTask(Runnable {
            val gui: GuiPlay = GameClient.currentGui as GuiPlay
            val entity = gui.world.entityMap[packet.uuid]!!
            entity.motionState.setWorldTransform(Transform(packet.transform))
            entity.body.proceedToTransform(Transform(packet.transform))
        })
    }
}