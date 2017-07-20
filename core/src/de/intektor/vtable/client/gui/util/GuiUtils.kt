package de.intektor.vtable.client.gui.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import de.intektor.vtable.GameClient
import javax.vecmath.Point2f

/**
 * @author Intektor
 */
fun isPointInRegion(x: Float, y: Float, width: Float, height: Float, pX: Float, pY: Float): Boolean {
    return pX >= x && pX <= x + width && pY >= y && pY <= y + height
}

fun unscaleScreenCoordX(x: Float): Float {
    val scale = GameClient.preferredScreenWidth / Gdx.graphics.width
    return (x / scale)
}

fun scaleScreenCoordX(x: Float): Float {
    val scale = GameClient.preferredScreenWidth / Gdx.graphics.width
    return (x * scale)
}

fun unscaleScreenCoordY(y: Float): Float {
    val scale = GameClient.preferredScreenHeight / Gdx.graphics.height
    return (y / scale)
}

fun scaleScreenCoordY(y: Float): Float {
    val scale = GameClient.preferredScreenHeight / Gdx.graphics.height
    return (y * scale)
}

fun OrthographicCamera.unprojectMousePosition(mouseX: Int = Gdx.input.x, mouseY: Int = Gdx.input.y): Point2f {
    val unProjected = this.unproject(Vector3(mouseX.toFloat(), mouseY.toFloat(), 0f))
    return Point2f(unProjected.x, unProjected.y)
}

fun scaleMouseX(): Float = scaleMouseX(Gdx.input.x)

fun scaleMouseX(mouseX: Int = Gdx.input.x): Float {
    val scale = GameClient.preferredScreenWidth / Gdx.graphics.width
    return (mouseX * scale)
}

fun scaleMouseY(): Float = scaleMouseY(Gdx.input.y)


fun scaleMouseY(mouseY: Int = Gdx.input.y): Float {
    val scale = GameClient.preferredScreenHeight / Gdx.graphics.height
    return GameClient.preferredScreenHeight - (mouseY * scale)
}