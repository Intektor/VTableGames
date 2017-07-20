package de.intektor.vtable.common.world.entity.test


import com.bulletphysics.collision.shapes.BoxShape
import com.bulletphysics.collision.shapes.CollisionShape
import de.intektor.vtable.common.world.World
import de.intektor.vtable.common.world.entity.Entity
import java.util.*
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
class EntityTestCube : Entity {

    override val canBePickedUp: Boolean = true
    override val shouldSendWorldTransformUpdates: Boolean = true

    constructor(uuid: UUID) : super(uuid)

    constructor(posX: Float, posY: Float, posZ: Float, world: World) : super(posX, posY, posZ, world)

    override fun getShape(): CollisionShape = BoxShape(Vector3f(0.5f, 0.5f, 0.5f))

    override fun getMass(): Float = 0.1f

}