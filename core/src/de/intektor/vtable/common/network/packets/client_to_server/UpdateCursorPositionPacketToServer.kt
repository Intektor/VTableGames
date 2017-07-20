package de.intektor.vtable.common.network.packets.client_to_server

import de.intektor.vtable.common.GameServer
import de.intektor.vtable.common.network.*
import de.intektor.vtable.common.network.packets.server_to_client.UpdateCursorPositionPacketToClient
import java.io.DataInputStream
import java.io.DataOutputStream
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
class UpdateCursorPositionPacketToServer : IPacket {

    private lateinit var position: Vector3f

    constructor(position: Vector3f) {
        this.position = position
    }

    constructor()

    override fun write(output: DataOutputStream) {
        position.write(output)
    }

    override fun read(input: DataInputStream) {
        position = readVector3f(input)
    }

    class Handler : IPacketHandler<UpdateCursorPositionPacketToServer> {

        override fun handlePacket(packet: UpdateCursorPositionPacketToServer, from: ISocket) {
            GameServer.mainThread.addScheduledTask(Runnable {
                val player = GameServer.playerMap[from]!!
                player.pointerPosition.set(packet.position)
                GameServer.broadcast(UpdateCursorPositionPacketToClient(player.userID, packet.position))
            })
        }
    }
}