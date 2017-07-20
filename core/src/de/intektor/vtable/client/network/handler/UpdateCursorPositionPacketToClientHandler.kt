package de.intektor.vtable.client.network.handler

import de.intektor.vtable.GameClient
import de.intektor.vtable.client.guis.GuiPlay
import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import de.intektor.vtable.common.network.packets.server_to_client.UpdateCursorPositionPacketToClient

/**
 * @author Intektor
 */
class UpdateCursorPositionPacketToClientHandler : IPacketHandler<UpdateCursorPositionPacketToClient> {

    override fun handlePacket(packet: UpdateCursorPositionPacketToClient, from: ISocket) {
        GameClient.addScheduledTask(Runnable {
            val gui: GuiPlay = GameClient.currentGui as GuiPlay
            gui.world.playerMap[packet.playerUUID]!!.pointerPosition = packet.position
        })
    }
}