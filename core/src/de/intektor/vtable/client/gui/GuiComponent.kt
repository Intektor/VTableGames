package de.intektor.vtable.client.gui

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import de.intektor.vtable.client.gui.util.isPointInRegion

/**
 * @author Intektor
 */
open class GuiComponent(internal var x: Int, internal var y: Int, internal var width: Int, internal var height: Int) : ClientRenderer() {

    internal var isShown = true
    internal var isEnabled = true

    internal var gui: Gui? = null

    /**
     * Gets called every renderInEditor tick, used to renderInEditor the component
     */
    open fun renderComponent(drawX: Float, drawY: Float, mouseX: Float, mouseY: Float, camera: OrthographicCamera, sR: ShapeRenderer, sB: SpriteBatch) {

    }

    /**
     * Gets called every updateWorld tick, used to updateWorld the component
     */
    open fun updateComponent(mouseX: Float, mouseY: Float, drawX: Float, drawY: Float) {

    }

    /**
     * Gets called when the user clicks somewhere on the gui
     */
    open fun clickDown(mouseX: Float, mouseY: Float, pointer: Int, button: Int, drawX: Float, drawY: Float) {

    }

    /**
     * Gets called as long as the user holds the click
     */
    open fun clicked(mouseX: Float, mouseY: Float, pointer: Int) {

    }

    /**
     * Gets called when the user releases his click from the gui
     */
    open fun clickUp(mouseX: Float, mouseY: Float, pointer: Int, button: Int, drawX: Float, drawY: Float) {

    }

    /**
     * Gets called when the user just pressed a key
     */
    open fun keyDown(mouseX: Float, mouseY: Float, keyCode: Int) {

    }

    /**
     * Gets called as long the user is pressing a key
     */
    open fun keyPressed(mouseX: Float, mouseY: Float, keyCode: Int) {

    }

    /**
     * Gets called when the user releases a key
     */
    open fun keyReleased(mouseX: Float, mouseY: Float, keyCode: Int) {

    }

    /**
     * Gets called when the user types a character
     */
    open fun charTyped(mouseX: Float, mouseY: Float, character: Char) {

    }

    /**
     * Gets called when the user moves the mouse without having it clicked
     */
    open fun mouseMoved(mouseX: Float, mouseY: Float, prevX: Float, prevY: Float) {

    }

    /**
     * Gets called when the user clicks and moves his pointer
     */
    open fun clickDragged(mouseX: Float, mouseY: Float, prevX: Float, prevY: Float, pointer: Int) {

    }

    /**
     * Gets called when the user scrolls with his scroll wheel
     */
    open fun scroll(mouseX: Float, mouseY: Float, scrollAmount: Int) {

    }

    /**
     * Moves the component relative to its current position
     */
    open fun moveComponent(amountX: Int, amountY: Int) {
        x += amountX
        y += amountY
    }

    /**
     * Sets the position of this component
     */
    open fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    /**
     * Called by [Gui.registerComponent] to set the current gui.
     * A GuiComponent can only function in one [Gui] at once, otherwise problems might occur!

     * @param currentGui the new gui instance
     */
    open fun setCurrentGui(currentGui: Gui?) {
        this.gui = currentGui
    }

    /**
     * @return whether the component is hovered with the mouse
     */
    open fun isHovered(mouseX: Float, mouseY: Float): Boolean {
        return isPointInRegion(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), mouseX, mouseY) && isShown
    }

    open fun onFling(velocityX: Float, velocityY: Float, button: Int) {

    }
}