package de.intektor.vtable.common.player

import com.badlogic.gdx.graphics.Color
import de.intektor.vtable.common.network.readUUID
import de.intektor.vtable.common.network.write
import de.intektor.vtable.util.Serializable
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*
import javax.vecmath.Vector3f

/**
 * @author Intektor
 */
class Player : Serializable {

    lateinit var username: String
    lateinit var userID: UUID
    lateinit var color: Color

    var pointerPosition: Vector3f = Vector3f()

    constructor(username: String, userID: UUID) {
        this.username = username
        this.userID = userID
        color = Color.RED
    }

    constructor()

    override fun writeToStream(output: DataOutputStream) {
        output.writeUTF(username)
        userID.write(output)
        output.writeInt(color.toIntBits())
    }

    override fun readFromStream(input: DataInputStream) {
        username = input.readUTF()
        userID = readUUID(input)
        color = Color(input.readInt())
    }

}