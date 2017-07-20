package de.intektor.vtable.common.network

/**
 * @author Intektor
 */
class PacketRegistry {

    private val packetRegistry = HashMap<Class<out IPacket>, RegistryEntry>()
    private val idToPacketClass = HashMap<Int, Class<out IPacket>>()

    fun registerPacket(packet: Class<out IPacket>, side: NetworkSide) {
        val id = packetRegistry.size
        packetRegistry.put(packet, RegistryEntry(null, id, side))
        idToPacketClass.put(id, packet)
    }

    fun <T : IPacket> registerPacket(packet: Class<T>, handler: IPacketHandler<T>, side: NetworkSide) {
        val id = packetRegistry.size
        packetRegistry.put(packet, RegistryEntry(handler, id, side))
        idToPacketClass.put(id, packet)
    }

    fun <T : IPacket> registerHandler(packet: Class<T>, handler: IPacketHandler<T>) {
        packetRegistry[packet]!!.handler = handler
    }

    fun getPacket(id: Int): IPacket = idToPacketClass[id]!!.newInstance()

    fun <T : IPacket> getPacketId(clazz: Class<T>): Int = packetRegistry[clazz]!!.id

    fun <T : IPacket> getHandler(clazz: Class<T>): IPacketHandler<T> = packetRegistry[clazz]!!.handler as IPacketHandler<T>

    fun <T : IPacket> getSide(clazz: Class<T>): NetworkSide = packetRegistry[clazz]!!.side

    private class RegistryEntry(var handler: IPacketHandler<*>?, val id: Int, val side: NetworkSide)
}