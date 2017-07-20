package de.intektor.vtable.client.network.handler

import de.intektor.vtable.common.network.IPacketHandler
import de.intektor.vtable.common.network.ISocket
import de.intektor.vtable.common.network.packets.server_to_client.KickPacketToClient

/**
 * @author Intektor
 */
class KickPacketToClientHandler : IPacketHandler<KickPacketToClient> {

    override fun handlePacket(packet: KickPacketToClient, from: ISocket) {

    }

}