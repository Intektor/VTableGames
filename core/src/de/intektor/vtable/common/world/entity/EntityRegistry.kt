package de.intektor.vtable.common.world.entity

import com.google.common.collect.HashBiMap
import de.intektor.vtable.common.world.entity.schafkopfn.EntityCard
import de.intektor.vtable.common.world.entity.test.EntityTestCube
import java.lang.reflect.Constructor
import java.util.*

/**
 * @author Intektor
 */
object EntityRegistry {

    private val registry: HashBiMap<Int, Class<out Entity>> = HashBiMap.create()
    private val constructorRegistry: HashBiMap<Int, Constructor<out Entity>> = HashBiMap.create()

    fun register() {
        register(EntityGround::class.java)
        register(EntityTestCube::class.java)
        register(EntityCard::class.java)
    }

    private fun register(clazz: Class<out Entity>) {
        registry.put(registry.size, clazz)
        constructorRegistry.put(constructorRegistry.size, clazz.getConstructor(UUID::class.java))
    }

    fun getID(clazz: Class<out Entity>): Int = registry.inverse()[clazz]!!

    fun create(id: Int, uuid: UUID): Entity = constructorRegistry[id]!!.newInstance(uuid)

}