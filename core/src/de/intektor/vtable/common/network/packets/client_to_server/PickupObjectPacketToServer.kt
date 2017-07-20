package de.intektor.vtable.common.network.packets.client_to_server

import de.intektor.vtable.common.CommonCode
import de.intektor.vtable.common.GameServer
import de.intektor.vtable.common.network.*
import de.intektor.vtable.common.network.packets.server_to_client.PickupObjectPacketToClient
import de.intektor.vtable.common.player.Player
import de.intektor.vtable.common.world.entity.Entity
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*

/**
 * @author Intektor
 */
class PickupObjectPacketToServer : IPacket {

    private lateinit var uuid: UUID

    constructor(uuid: UUID) {
        this.uuid = uuid
    }

    constructor()

    override fun write(output: DataOutputStream) {
        uuid.write(output)
    }

    override fun read(input: DataInputStream) {
        uuid = readUUID(input)
    }

    class Handler : IPacketHandler<PickupObjectPacketToServer> {

        override fun handlePacket(packet: PickupObjectPacketToServer, from: ISocket) {
            GameServer.mainThread.addScheduledTask(Runnable {
                val entity: Entity = GameServer.mainThread.world.entityMap[packet.uuid]!!
                val player: Player = GameServer.playerMap[from]!!
                if (entity.canBePickedUp) {
                    entity.playerPickedUp = player
                    entity.pickup()
                    PickupObjectPacketToClient(entity.uuid, player.userID).sendTo(from, CommonCode.packetRegistry)
                }
            })
        }
    }
}