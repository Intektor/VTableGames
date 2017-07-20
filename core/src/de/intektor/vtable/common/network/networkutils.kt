package de.intektor.vtable.common.network

import de.intektor.vtable.util.Serializable
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*
import javax.vecmath.Matrix4f
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
fun UUID.write(output: DataOutputStream) {
    output.writeLong(this.mostSignificantBits)
    output.writeLong(this.leastSignificantBits)
}

fun readUUID(input: DataInputStream): UUID = UUID(input.readLong(), input.readLong())

fun Matrix4f.write(output: DataOutputStream) {
    output.writeFloat(m00)
    output.writeFloat(m01)
    output.writeFloat(m02)
    output.writeFloat(m03)
    output.writeFloat(m10)
    output.writeFloat(m11)
    output.writeFloat(m12)
    output.writeFloat(m13)
    output.writeFloat(m20)
    output.writeFloat(m21)
    output.writeFloat(m22)
    output.writeFloat(m23)
    output.writeFloat(m30)
    output.writeFloat(m31)
    output.writeFloat(m32)
    output.writeFloat(m33)
}

fun readMatrix(input: DataInputStream): Matrix4f
        = Matrix4f(
        input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
        input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
        input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
        input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat())

fun Vector3f.write(output: DataOutputStream) {
    output.writeFloat(x)
    output.writeFloat(y)
    output.writeFloat(z)
}

fun readVector3f(input: DataInputStream) = Vector3f(input.readFloat(), input.readFloat(), input.readFloat())

fun List<Serializable>.write(output: DataOutputStream) {
    output.writeInt(size)
    for (serializable in this) {
        serializable.writeToStream(output)
    }
}

inline fun <reified T : Serializable> readList(input: DataInputStream): List<T> {
    val list = mutableListOf<T>()
    for (i in 0..input.readInt() - 1) {
        val item: T = T::class.java.newInstance()
        item.readFromStream(input)
        list.add(item)
    }
    return list
}