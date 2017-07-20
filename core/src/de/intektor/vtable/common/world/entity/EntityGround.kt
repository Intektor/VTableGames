package de.intektor.vtable.common.world.entity

import com.bulletphysics.collision.shapes.BoxShape
import com.bulletphysics.collision.shapes.CollisionShape
import de.intektor.vtable.common.world.World
import java.util.*
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
class EntityGround : Entity {

    override val canBePickedUp: Boolean = false
    override val shouldSendWorldTransformUpdates: Boolean = false

    constructor(world: World) : super(0f, 0f, 0f, world)

    constructor(uuid: UUID) : super(uuid)

    override fun getShape(): CollisionShape = BoxShape(Vector3f(50f, 2.5f, 50f))
//    override fun getShape(): CollisionShape = StaticPlaneShape(Vector3f(0f, 0f, 0f), 1f)

    override fun getMass(): Float = 0f
}