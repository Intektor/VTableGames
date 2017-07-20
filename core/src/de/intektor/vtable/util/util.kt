package de.intektor.vtable.util

import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.bulletphysics.collision.dispatch.CollisionObject
import de.intektor.vtable.common.world.entity.Entity
import javax.vecmath.Matrix3f
import javax.vecmath.Matrix4f
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
fun CollisionObject.entity(): Entity = this.userPointer as Entity

fun Matrix4f.morph(): Matrix4 = Matrix4(floatArrayOf(m00, m01, m02, m03,
        m10, m11, m12, m13,
        m20, m21, m22, m23,
        m30, m31, m32, m33))

fun Matrix3f.morph(): Matrix3 = Matrix3(floatArrayOf(
        m00, m01, m02,
        m10, m11, m12,
        m20, m21, m22))

fun Matrix3.morph(): Matrix3f = Matrix3f()

fun Matrix4.morph(): Matrix4f = Matrix4f(this.`val`)

fun Vector3f.morph(): Vector3 = Vector3(x, y, z)

fun Vector3.morph(): Vector3f = Vector3f(x, y, z)

public inline fun Vector3f.add(a: Vector3f): Vector3f = this.add(a.x, a.y, a.z)

public inline fun Vector3f.add(x: Float, y: Float, z: Float): Vector3f {
    this.x += x
    this.y += y
    this.z += z
    return this
}

public inline fun Vector3f.sub(a: Vector3f): Vector3f = this.sub(a.x, a.y, a.z)

public inline fun Vector3f.sub(x: Float, y: Float, z: Float): Vector3f {
    this.x -= x
    this.y -= y
    this.z -= z
    return this
}

public inline fun Vector3f.distance(a: Vector3f): Float {
    val dX = x - a.x
    val dY = y - a.y
    val dZ = z - a.z
    return Math.sqrt((dX * dX + dY * dY + dZ * dZ).toDouble()).toFloat()
}