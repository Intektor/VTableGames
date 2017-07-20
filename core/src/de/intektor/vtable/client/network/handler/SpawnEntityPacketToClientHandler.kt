package de.intektor.vtable.client.network.handler

import de.intektor.vtable.GameClient
import de.intektor.vtable.client.guis.GuiPlay
import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import de.intektor.vtable.common.network.packets.server_to_client.SpawnEntityPacketToClient

/**
 * @author Intektor
 */
class SpawnEntityPacketToClientHandler : IPacketHandler<SpawnEntityPacketToClient> {

    override fun handlePacket(packet: SpawnEntityPacketToClient, from: ISocket) {
        GameClient.addScheduledTask(Runnable {
            val gui: GuiPlay = GameClient.currentGui as GuiPlay
            gui.world.spawnEntityInWorld(packet.entity)
        })
    }
}