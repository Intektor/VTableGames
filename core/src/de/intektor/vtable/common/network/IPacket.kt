package de.intektor.vtable.common.network

import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author Intektor
 */
interface IPacket {

    fun write(output: DataOutputStream)

    fun read(input: DataInputStream)
}

fun IPacket.sendTo(to: ISocket, registry: PacketRegistry) {
    val output = DataOutputStream(to.getOutputStream())
    output.writeInt(registry.getPacketId(this.javaClass))
    this.write(output)
}

fun readAndHandlePacket(from: DataInputStream, registry: PacketRegistry, side: NetworkSide, socket: ISocket) {
    val packet = registry.getPacket(from.readInt())
    registry.getSide(packet.javaClass)
    packet.read(from)
    registry.getHandler(packet.javaClass).handlePacket(packet, socket)
}