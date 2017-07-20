package de.intektor.vtable.client.renderer.entity

import de.intektor.vtable.client.renderer.entity.renderers.EntityCardRenderer
import de.intektor.vtable.client.renderer.entity.renderers.EntityGroundRenderer
import de.intektor.vtable.client.renderer.entity.renderers.EntityTestCubeRenderer
import de.intektor.vtable.common.world.entity.Entity
import de.intektor.vtable.common.world.entity.EntityGround
import de.intektor.vtable.common.world.entity.schafkopfn.EntityCard
import de.intektor.vtable.common.world.entity.test.EntityTestCube

/**
 * @author Intektor
 */
object EntityRenderRegistry {

    private val registry: HashMap<Class<out Entity>, IEntityRenderer<*>> = HashMap()

    init {
        register(EntityTestCube::class.java, EntityTestCubeRenderer())
        register(EntityGround::class.java, EntityGroundRenderer())
        register(EntityCard::class.java, EntityCardRenderer())
    }

    fun <T : Entity> register(clazz: Class<T>, renderer: IEntityRenderer<T>) {
        registry.put(clazz, renderer)
    }

    fun <T : Entity> getRenderer(clazz: Class<T>): IEntityRenderer<T> = registry[clazz] as IEntityRenderer<T>
}