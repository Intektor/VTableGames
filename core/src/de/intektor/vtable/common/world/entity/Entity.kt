package de.intektor.vtable.common.world.entity

import com.badlogic.gdx.utils.Disposable
import com.bulletphysics.collision.shapes.CollisionShape
import com.bulletphysics.dynamics.RigidBody
import com.bulletphysics.dynamics.RigidBodyConstructionInfo
import com.bulletphysics.linearmath.Transform
import de.intektor.vtable.client.renderer.entity.MotionState
import de.intektor.vtable.common.player.Player
import de.intektor.vtable.common.world.World
import de.intektor.vtable.util.add
import de.intektor.vtable.util.distance
import de.intektor.vtable.util.sub
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*
import javax.vecmath.Vector3f


/**
 * @author Intektor
 */
abstract class Entity : Disposable {

    val uuid: UUID
    val body: RigidBody
    val localInertia: Vector3f = Vector3f()
    val motionState: MotionState = MotionState()

    val lastGravity: Vector3f = Vector3f()

    lateinit var world: World

    abstract val canBePickedUp: Boolean

    abstract val shouldSendWorldTransformUpdates: Boolean

    val pickedUp: Boolean
        get() = playerPickedUp != null

    var playerPickedUp: Player? = null

    constructor(posX: Float, posY: Float, posZ: Float, world: World) {
        this.world = world
        uuid = UUID.randomUUID()
        motionState.transform.origin.set(Vector3f(posX, posY, posZ))
        body.proceedToTransform(motionState.transform)
    }

    constructor(uuid: UUID) {
        this.uuid = uuid
    }

    init {
        val mass = getMass()
        val shape = getShape()
        if (mass > 0f) shape.calculateLocalInertia(mass, localInertia)
        else localInertia.set(0f, 0f, 0f)
        val cI = RigidBodyConstructionInfo(mass, motionState, shape, localInertia)
        body = RigidBody(cI)
        body.motionState = motionState
        body.proceedToTransform(motionState.transform)
        body.collisionFlags = body.collisionFlags
        body.userPointer = this
        body.collisionFlags = 0
    }

    fun update() {
        if (pickedUp) {
            val player = playerPickedUp!!
            val dragTo = Vector3f(player.pointerPosition).add(0f, 10f, 0f)
            val current = body.getWorldTransform(Transform()).origin
            val distance = dragTo.distance(current)
            if (distance > 1) {
                val scale: Float
                val maxV = 10f
                if (distance > 10) {
                    scale = maxV
                } else {
                    scale = maxV * (distance / 10f)
                }
                dragTo.sub(current.x, current.y, current.z).scale(scale)
                body.setLinearVelocity(dragTo)
            }
        }
    }

    abstract fun getShape(): CollisionShape

    abstract fun getMass(): Float

    override fun dispose() {

    }

    fun pickup() {
        body.activate(true)
        lastGravity.set(body.getGravity(Vector3f()))
        body.setGravity(Vector3f(0f, 0f, 0f))
    }

    fun drop() {
        body.setGravity(lastGravity)
    }

    fun writeSpawnData(output: DataOutputStream) {}

    fun readSpawnData(input: DataInputStream) {}
}