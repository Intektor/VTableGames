package de.intektor.vtable.common.network.packets.server_to_client

import de.intektor.vtable.common.network.IPacket
import de.intektor.vtable.common.network.readList
import de.intektor.vtable.common.network.write
import de.intektor.vtable.common.player.Player
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author Intektor
 */
class ServerInfoPacketToClient : IPacket {

    var playerList: List<Player> = listOf()

    constructor(playerList: List<Player>) {
        this.playerList = playerList
    }

    constructor()

    override fun write(output: DataOutputStream) {
        playerList.write(output)
    }

    override fun read(input: DataInputStream) {
        playerList = readList<Player>(input)
    }

}