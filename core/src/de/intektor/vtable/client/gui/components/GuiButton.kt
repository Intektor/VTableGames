package de.intektor.vtable.client.gui.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import de.intektor.vtable.client.gui.Gui
import de.intektor.vtable.client.gui.GuiComponent
import de.intektor.vtable.client.gui.util.isPointInRegion
import de.intektor.vtable.client.gui.util.unscaleScreenCoordX
import de.intektor.vtable.client.gui.util.unscaleScreenCoordY

/**
 * @author Intektor
 */
abstract class GuiButton(x: Int, y: Int, width: Int, height: Int) : GuiComponent(x, y, width, height) {

    /**
     * Indicates whether the user started the click on this button
     */
    private var clickStarted: Boolean = false

    private var clickX: Float = 0f
    private var clickY: Float = 0f
    private var clickTicks: Long = 0

    var callback: GuiButtonCallback? = null

    override fun renderComponent(drawX: Float, drawY: Float, mouseX: Float, mouseY: Float, camera: OrthographicCamera, sR: ShapeRenderer, sB: SpriteBatch) {
        super.renderComponent(drawX, drawY, mouseX, mouseY, camera, sR, sB)
        renderButton(drawX, drawY, mouseX, mouseY, camera, sR, sB)
        if (clickStarted) {
            enableBlending()
            sR.begin(ShapeRenderer.ShapeType.Filled)
            sR.color = Color(0.5f, 0.5f, 0.5f, 0.3f)
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST)
            sR.circle(clickX.toFloat(), clickY.toFloat(), (clickTicks * 60).toFloat())
            Gdx.gl.glScissor(unscaleScreenCoordX(drawX).toInt(), unscaleScreenCoordY(drawY).toInt(), unscaleScreenCoordX(width.toFloat()).toInt(), unscaleScreenCoordY(height.toFloat()).toInt())
            sR.end()
            disableBlending()
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST)
        }
    }


    override fun updateComponent(mouseX: Float, mouseY: Float, drawX: Float, drawY: Float) {
        super.updateComponent(mouseX, mouseY, drawX, drawY)
        clickTicks++
    }

    protected abstract fun renderButton(drawX: Float, drawY: Float, mouseX: Float, mouseY: Float, camera: OrthographicCamera, sR: ShapeRenderer, sB: SpriteBatch)

    override fun clickDown(mouseX: Float, mouseY: Float, pointer: Int, button: Int, drawX: Float, drawY: Float) {
        super.clickDown(mouseX, mouseY, pointer, button, drawX, drawY)
        if (isPointInRegion(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), mouseX.toFloat(), mouseY.toFloat()) && isEnabled && isShown) {
            clickStarted = true
            clickX = mouseX
            clickY = mouseY
            clickTicks = 0
        }
    }

    override fun clickUp(mouseX: Float, mouseY: Float, pointer: Int, button: Int, drawX: Float, drawY: Float) {
        super.clickUp(mouseX, mouseY, pointer, button, drawX, drawY)
        if (isPointInRegion(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), mouseX.toFloat(), mouseY.toFloat()) && clickStarted && isShown) {
            callback!!.buttonCallback(this)
        }
        clickStarted = false
    }
    override fun setCurrentGui(currentGui: Gui?) {
        super.setCurrentGui(currentGui)
        callback = currentGui
    }

    interface GuiButtonCallback {

        /**
         * Gets called by [GuiComponent.clickUp] when the user successfully clicked and released the pointer on a button
         */
        fun buttonCallback(button: GuiButton)
    }
}