package de.intektor.vtable.common.network

/**
 * @author Intektor
 */
interface IPacketHandler<in T : IPacket> {

    fun handlePacket(packet: T, from: ISocket)
}