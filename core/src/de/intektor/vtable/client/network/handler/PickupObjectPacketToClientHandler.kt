package de.intektor.vtable.client.network.handler

import de.intektor.vtable.GameClient
import de.intektor.vtable.client.guis.GuiPlay
import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import de.intektor.vtable.common.network.packets.server_to_client.PickupObjectPacketToClient

/**
 * @author Intektor
 */
class PickupObjectPacketToClientHandler : IPacketHandler<PickupObjectPacketToClient> {

    override fun handlePacket(packet: PickupObjectPacketToClient, from: ISocket) {
        GameClient.addScheduledTask(Runnable {
            val gui: GuiPlay = GameClient.currentGui as GuiPlay
            val entity = gui.world.entityMap[packet.entityUUID]!!
            entity.playerPickedUp = gui.world.playerMap[packet.playerUUID]!!
            entity.pickup()
        })
    }

}