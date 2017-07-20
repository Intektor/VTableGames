package de.intektor.vtable.client.guis

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.Vector3
import com.bulletphysics.collision.dispatch.CollisionObject
import com.bulletphysics.dynamics.RigidBody
import de.intektor.vtable.GameClient
import de.intektor.vtable.client.gui.Gui
import de.intektor.vtable.client.network.ClientConnection
import de.intektor.vtable.client.renderer.WorldRenderer
import de.intektor.vtable.client.world.WorldClient
import de.intektor.vtable.common.network.packets.client_to_server.DropObjectPacketToServer
import de.intektor.vtable.common.network.packets.client_to_server.PickupObjectPacketToServer
import de.intektor.vtable.common.network.packets.client_to_server.UpdateCursorPositionPacketToServer
import de.intektor.vtable.util.entity
import javax.vecmath.Vector3f


/**
 * @author Intektor
 */
class GuiPlay : Gui() {

    val worldCamera: PerspectiveCamera = PerspectiveCamera(75f, 1280f, 720f)
    val world: WorldClient = WorldClient()
    val worldModelBatch: ModelBatch

    var cameraYaw = 0f
    var cameraPitch = 0f

    val cameraOrigin: Vector3 = Vector3(-50f, 25f, 0f)

    init {
        worldCamera.far = 500f
        worldModelBatch = ModelBatch()
        worldCamera.position.set(-100f, 10f, -10f)
    }

    override fun renderGui(mouseX: Float, mouseY: Float, camera: OrthographicCamera) {
        super.renderGui(mouseX, mouseY, camera)
        worldCamera.update()
        WorldRenderer.renderWorld(world, GameClient.partialTicks, worldCamera, worldModelBatch, spriteBatch)
    }


    override fun updateGui(mouseX: Float, mouseY: Float) {
        super.updateGui(mouseX, mouseY)

        world.updateWorld()

        if (forward) cameraOrigin.add(Math.cos(Math.toRadians(cameraYaw.toDouble())).toFloat(), 0f, -Math.sin(Math.toRadians(cameraYaw.toDouble())).toFloat())
        if (backward) cameraOrigin.add(-Math.cos(Math.toRadians(cameraYaw.toDouble())).toFloat(), 0f, Math.sin(Math.toRadians(cameraYaw.toDouble())).toFloat())
        if (right) cameraOrigin.add(Math.cos(Math.toRadians(cameraYaw.toDouble() - 90)).toFloat(), 0f, -Math.sin(Math.toRadians(cameraYaw.toDouble() - 90)).toFloat())
        if (left) cameraOrigin.add(Math.cos(Math.toRadians(cameraYaw.toDouble() + 90)).toFloat(), 0f, -Math.sin(Math.toRadians(cameraYaw.toDouble() + 90)).toFloat())

        if (up) cameraOrigin.add(0f, 1f, 0f)
        if (down) cameraOrigin.add(0f, -1f, 0f)

        worldCamera.position.set(cameraOrigin)

        if (rotateUp) {
            cameraPitch += 5f
            if (cameraPitch > 90) cameraPitch = 90f
        }
        if (rotateDown) {
            cameraPitch += -5f
            if (cameraPitch < -90) cameraPitch = -90f
        }
        if (rotateRight) cameraYaw += -5f
        if (rotateLeft) cameraYaw += 5f

        worldCamera.up.set(0f, 1f, 0f)
        worldCamera.direction.set(0f, 0f, -1f)
        worldCamera.rotate(cameraPitch, 1f, 0f, 0f)
        worldCamera.rotate(cameraYaw - 90, 0f, 1f, 0f)

        worldCamera.update()
    }

    var forward = false
    var backward = false
    var right = false
    var left = false
    var up = false
    var down = false
    var rotateUp = false
    var rotateDown = false
    var rotateRight = false
    var rotateLeft = false

    override fun keyPushed(keyCode: Int, mouseX: Float, mouseY: Float) {
        super.keyPushed(keyCode, mouseX, mouseY)
        when (keyCode) {
            Input.Keys.W -> forward = true
            Input.Keys.D -> right = true
            Input.Keys.S -> backward = true
            Input.Keys.A -> left = true
            Input.Keys.UP -> rotateUp = true
            Input.Keys.RIGHT -> rotateRight = true
            Input.Keys.DOWN -> rotateDown = true
            Input.Keys.LEFT -> rotateLeft = true
            Input.Keys.SPACE -> up = true
            Input.Keys.SHIFT_LEFT -> down = true
        }
    }

    override fun keyReleased(keyCode: Int, mouseX: Float, mouseY: Float) {
        super.keyReleased(keyCode, mouseX, mouseY)
        when (keyCode) {
            Input.Keys.W -> forward = false
            Input.Keys.D -> right = false
            Input.Keys.S -> backward = false
            Input.Keys.A -> left = false
            Input.Keys.UP -> rotateUp = false
            Input.Keys.RIGHT -> rotateRight = false
            Input.Keys.DOWN -> rotateDown = false
            Input.Keys.LEFT -> rotateLeft = false
            Input.Keys.SPACE -> up = false
            Input.Keys.SHIFT_LEFT -> down = false
        }
    }

    var currentHeldObject: RigidBody? = null

    override fun pointerDown(mouseX: Float, mouseY: Float, pointer: Int, button: Int) {
        super.pointerDown(mouseX, mouseY, pointer, button)
        ClientConnection.sendPacketToServer(UpdateCursorPositionPacketToServer(getPointerPosition(0)))
        if (lastHoveredCollisionObject != null) {
            currentHeldObject = lastHoveredCollisionObject as RigidBody
            ClientConnection.sendPacketToServer(PickupObjectPacketToServer(currentHeldObject!!.entity().uuid))
        }
    }

    override fun pointerDragged(mouseX: Float, mouseY: Float, prevMouseX: Float, prevMouseY: Float, pointer: Int) {
        super.pointerDragged(mouseX, mouseY, prevMouseX, prevMouseY, pointer)
        ClientConnection.sendPacketToServer(UpdateCursorPositionPacketToServer(getPointerPosition(0)))
    }

    override fun pointerUp(mouseX: Float, mouseY: Float, pointer: Int, button: Int) {
        super.pointerUp(mouseX, mouseY, pointer, button)
        if (currentHeldObject != null) {
            ClientConnection.sendPacketToServer(DropObjectPacketToServer(currentHeldObject!!.entity().uuid))
            currentHeldObject = null
        }
    }

    override fun exitGui() {

    }

    var lastHoveredCollisionObject: CollisionObject? = null

    fun getPointerPosition(pointer: Int): Vector3f {
        val ray = worldCamera.getPickRay(Gdx.input.getX(pointer).toFloat(), Gdx.input.getY(pointer).toFloat())
        val tmpV1 = Vector3(ray.direction).scl(100f).add(ray.origin)
//        val cb = AllHitsRayResultCallback(ray.origin, tmpV1)
//        world.world.rayTest(ray.origin, tmpV1, cb)
        val pos = Vector3f()
//        if (cb.hasHit()) {
//            if (cb.collisionObjects.at(0) != currentHeldObject) {
//                pos.set(cb.hitPointWorld.at(0))
//                lastHoveredCollisionObject = cb.collisionObjects.at(0)
//            } else {
//                pos.set(cb.hitPointWorld.at(1))
//                lastHoveredCollisionObject = cb.collisionObjects.at(1)
//            }
//        }
//        cb.release()
        return pos
    }
}