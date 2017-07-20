package de.intektor.vtable.client.renderer.entity

import com.bulletphysics.linearmath.Transform

/**
 * @author Intektor
 */
class MotionState : com.bulletphysics.linearmath.MotionState() {

    val transform: Transform = Transform()

    override fun getWorldTransform(out: Transform?): Transform = transform

    override fun setWorldTransform(worldTrans: Transform?) {
        transform.set(worldTrans)
    }
}