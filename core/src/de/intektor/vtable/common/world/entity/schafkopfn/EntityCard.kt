package de.intektor.vtable.common.world.entity.schafkopfn

import com.bulletphysics.collision.shapes.BoxShape
import com.bulletphysics.collision.shapes.CollisionShape
import de.intektor.vtable.common.world.World
import de.intektor.vtable.common.world.entity.Entity
import java.util.*
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
class EntityCard : Entity {

    constructor(posX: Float, posY: Float, posZ: Float, world: World) : super(posX, posY, posZ, world)

    constructor(uuid: UUID) : super(uuid)

    override val canBePickedUp: Boolean = true

    override val shouldSendWorldTransformUpdates: Boolean = true

    override fun getShape(): CollisionShape = BoxShape(Vector3f(9f, 0.01f, 6f))

    override fun getMass(): Float = 0.1f

}