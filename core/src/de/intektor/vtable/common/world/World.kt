package de.intektor.vtable.common.world

import com.badlogic.gdx.utils.Disposable
import com.bulletphysics.collision.broadphase.DbvtBroadphase
import com.bulletphysics.collision.dispatch.CollisionConfiguration
import com.bulletphysics.collision.dispatch.CollisionDispatcher
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration
import com.bulletphysics.dynamics.DiscreteDynamicsWorld
import com.bulletphysics.dynamics.DynamicsWorld
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver
import de.intektor.vtable.common.player.Player
import de.intektor.vtable.common.world.entity.Entity
import java.util.*
import javax.vecmath.Vector3f
import kotlin.collections.HashMap

/**
 * @author Intektor
 */
abstract class World(val isRemote: Boolean) : Disposable {

    val loadedEntityList: MutableList<Entity> = mutableListOf()
    val entityMap: HashMap<UUID, Entity> = HashMap()
    val playerMap: HashMap<UUID, Player> = HashMap()

    val broadphase: DbvtBroadphase = DbvtBroadphase()
    val collisionConfiguration: CollisionConfiguration = DefaultCollisionConfiguration()
    val dispatcher: CollisionDispatcher = CollisionDispatcher(collisionConfiguration)
    val solver: SequentialImpulseConstraintSolver = SequentialImpulseConstraintSolver()

    val world: DynamicsWorld = DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration)

    var currentTickCount: Int = 0

    init {
        world.setGravity(Vector3f(0f, -9.81f, 0f))
    }

    open fun updateWorld() {
        for (entity in loadedEntityList) {
            entity.update()
        }
        world.stepSimulation(1 / 32f)
        currentTickCount++
    }

    open fun spawnEntityInWorld(entity: Entity) {
        loadedEntityList.add(entity)
        entityMap.put(entity.uuid, entity)
        world.addRigidBody(entity.body)
    }

    override fun dispose() {
        for (entity in loadedEntityList) {
            entity.dispose()
        }
    }
}