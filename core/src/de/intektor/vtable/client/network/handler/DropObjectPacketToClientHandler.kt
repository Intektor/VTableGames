package de.intektor.vtable.client.network.handler

import de.intektor.vtable.GameClient
import de.intektor.vtable.client.guis.GuiPlay
import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import de.intektor.vtable.common.network.packets.server_to_client.DropObjectPacketToClient

/**
 * @author Intektor
 */
class DropObjectPacketToClientHandler : IPacketHandler<DropObjectPacketToClient> {

    override fun handlePacket(packet: DropObjectPacketToClient, from: ISocket) {
        GameClient.addScheduledTask(Runnable {
            val gui: GuiPlay = GameClient.currentGui as GuiPlay
            val entity = gui.world.entityMap[packet.entityUUID]!!
            entity.drop()
            entity.playerPickedUp = null
        })
    }
}