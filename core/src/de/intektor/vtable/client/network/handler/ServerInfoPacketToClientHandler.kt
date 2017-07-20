package de.intektor.vtable.client.network.handler

import de.intektor.vtable.GameClient
import de.intektor.vtable.client.guis.GuiPlay
import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import de.intektor.vtable.common.network.packets.server_to_client.ServerInfoPacketToClient

/**
 * @author Intektor
 */
class ServerInfoPacketToClientHandler : IPacketHandler<ServerInfoPacketToClient> {

    override fun handlePacket(packet: ServerInfoPacketToClient, from: ISocket) {
        GameClient.addScheduledTask(Runnable {
            val gui: GuiPlay = GameClient.currentGui as GuiPlay
            for (player in packet.playerList) {
                gui.world.playerMap.put(player.userID, player)
            }
        })
    }
}