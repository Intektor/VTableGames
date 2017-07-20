package de.intektor.vtable.util

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

/**
 * @author Intektor
 */
interface Serializable {

    @Throws(IOException::class)
    fun writeToStream(output: DataOutputStream)

    fun readFromStream(input: DataInputStream)
}