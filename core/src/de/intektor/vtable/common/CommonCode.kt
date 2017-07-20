package de.intektor.vtable.common

import de.intektor.vtable.common.network.NetworkSide
import de.intektor.vtable.common.network.PacketRegistry
import de.intektor.vtable.common.network.packets.client_to_server.DropObjectPacketToServer
import de.intektor.vtable.common.network.packets.client_to_server.PickupObjectPacketToServer
import de.intektor.vtable.common.network.packets.client_to_server.RegisterPacketToServer
import de.intektor.vtable.common.network.packets.client_to_server.UpdateCursorPositionPacketToServer
import de.intektor.vtable.common.network.packets.server_to_client.*
import de.intektor.vtable.common.world.entity.EntityRegistry

/**
 * @author Intektor
 */
object CommonCode {

    var packetRegistry: PacketRegistry = PacketRegistry()

    init {
        packetRegistry.registerPacket(KickPacketToClient::class.java, NetworkSide.CLIENT)
        packetRegistry.registerPacket(SpawnEntityPacketToClient::class.java, NetworkSide.CLIENT)
        packetRegistry.registerPacket(ServerInfoPacketToClient::class.java, NetworkSide.CLIENT)
        packetRegistry.registerPacket(UpdateCursorPositionPacketToClient::class.java, NetworkSide.CLIENT)
        packetRegistry.registerPacket(EntityUpdatePacketToClient::class.java, NetworkSide.CLIENT)
        packetRegistry.registerPacket(PickupObjectPacketToClient::class.java, NetworkSide.CLIENT)
        packetRegistry.registerPacket(DropObjectPacketToClient::class.java, NetworkSide.CLIENT)

        packetRegistry.registerPacket(DropObjectPacketToServer::class.java, DropObjectPacketToServer.Handler(), NetworkSide.SERVER)
        packetRegistry.registerPacket(UpdateCursorPositionPacketToServer::class.java, UpdateCursorPositionPacketToServer.Handler(), NetworkSide.SERVER)
        packetRegistry.registerPacket(PickupObjectPacketToServer::class.java, PickupObjectPacketToServer.Handler(), NetworkSide.SERVER)
        packetRegistry.registerPacket(RegisterPacketToServer::class.java, RegisterPacketToServer.Handler(), NetworkSide.SERVER)

        EntityRegistry.register()
    }
}