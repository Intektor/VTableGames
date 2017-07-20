package de.intektor.vtable.client.gui

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import de.intektor.vtable.GameClient
import de.intektor.vtable.client.gui.components.GuiButton
import de.intektor.vtable.client.gui.util.scaleMouseX
import de.intektor.vtable.client.gui.util.scaleMouseY
import java.util.*
import javax.vecmath.Point2f

/**
 * @author Intektor
 */
open class Gui : ClientRenderer(), InputProcessor, GestureDetector.GestureListener, GuiButton.GuiButtonCallback {

    var width: Float
    var height: Float
    var spriteBatch = GameClient.defaultSpriteBatch
    var shapeRenderer = GameClient.defaultShapeRenderer
    var modelBatch = GameClient.defaultModelBatch
    var camera = GameClient.camera

    var componentList: MutableList<GuiComponent> = ArrayList()
    var componentsToBeRemoved: MutableList<GuiComponent> = ArrayList()

    var input: Input
    var graphics: Graphics

    var allowInput = true

    private val lastPointerPositionMap = HashMap<Int, Point2f>()

    init {
        val multiplexer = InputMultiplexer(this, GestureDetector(this))
        Gdx.input.inputProcessor = multiplexer
        input = Gdx.input
        graphics = Gdx.graphics
        for (i in 0..9) {
            lastPointerPositionMap.put(i, Point2f(0f, 0f))
        }
        width = GameClient.preferredScreenWidth
        height = GameClient.preferredScreenHeight
    }

    fun render(mouseX: Int, mouseY: Int, camera: OrthographicCamera) {
        renderGui(scaleMouseX(mouseX), scaleMouseY(mouseY), camera)
    }

    open fun renderGui(mouseX: Float, mouseY: Float, camera: OrthographicCamera) {
        renderGuiComponents(mouseX, mouseY, camera)
    }


    fun renderGuiComponents(mouseX: Float, mouseY: Float, camera: OrthographicCamera) {
        for (guiComponent in componentList) {
            if (guiComponent.isShown) {
                guiComponent.renderComponent(guiComponent.x.toFloat(), guiComponent.y.toFloat(), mouseX, mouseY, camera, shapeRenderer, spriteBatch)
            }
        }
    }


    fun update(mouseX: Int, mouseY: Int) {
        updateGui(scaleMouseX(mouseX), scaleMouseY(mouseY))
    }

    open fun updateGui(mouseX: Float, mouseY: Float) {
        updateGuiComponents(mouseX, mouseY)
    }

    /**
     * Updates the gui components of this gui, default called by [Gui.updateGui]
     */
    fun updateGuiComponents(mouseX: Float, mouseY: Float) {
        componentList.removeAll(componentsToBeRemoved)
        componentsToBeRemoved.clear()
        for (guiComponent in componentList) {
            if (guiComponent.isEnabled) {
                guiComponent.updateComponent(mouseX, mouseY, guiComponent.x.toFloat(), guiComponent.y.toFloat())
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX()
        val mouseY = scaleMouseY()
        for (component in componentList) {
            if (component.isEnabled && component.isShown) {
                component.keyDown(mouseX, mouseY, keycode)
            }
        }
        keyPushed(keycode, mouseX, mouseY)
        return false
    }

    /**
     * Called by [Gui.keyDown] when the user pushed down a key, uses scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun keyPushed(keyCode: Int, mouseX: Float, mouseY: Float) {

    }

    override final fun keyUp(keycode: Int): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX()
        val mouseY = scaleMouseY()
        for (component in componentList) {
            if (component.isEnabled && component.isShown) {
                component.keyReleased(mouseX, mouseY, keycode)
            }
        }
        keyReleased(keycode, mouseX, mouseY)
        return false
    }

    /**
     * Called by [Gui.keyUp] when the user released a pushed down key, uses scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun keyReleased(keyCode: Int, mouseX: Float, mouseY: Float) {

    }

    override final fun keyTyped(character: Char): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX()
        val mouseY = scaleMouseY()
        componentList
                .filter { it.isEnabled && it.isShown }
                .forEach { it.charTyped(mouseX, mouseY, character) }
        charTyped(character, mouseX, mouseY)
        return false
    }

    /**
     * Called by [Gui.keyTyped] when the user types, uses scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun charTyped(character: Char, mouseX: Float, mouseY: Float) {

    }

    override final fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX(screenX)
        val mouseY = scaleMouseY(screenY)
        componentList
                .filter { it.isEnabled && it.isShown }
                .forEach { it.clickDown(mouseX, mouseY, pointer, button, it.x.toFloat(), it.y.toFloat()) }
        lastPointerPositionMap.put(pointer, Point2f(mouseX, mouseY))
        pointerDown(mouseX, mouseY, pointer, button)
        return false
    }

    /**
     * Called by [Gui.touchDown] when the user clicked the screen, uses scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun pointerDown(mouseX: Float, mouseY: Float, pointer: Int, button: Int) {

    }

    override final fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX(screenX)
        val mouseY = scaleMouseY(screenY)
        componentList
                .filter { it.isEnabled && it.isShown }
                .forEach { it.clickUp(mouseX, mouseY, pointer, button, it.x.toFloat(), it.y.toFloat()) }
        pointerUp(mouseX, mouseY, pointer, button)
        return false
    }

    /**
     * Called by [Gui.touchUp] when the user released his pointer from the screen, uses scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun pointerUp(mouseX: Float, mouseY: Float, pointer: Int, button: Int) {

    }

    override final fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX(screenX)
        val mouseY = scaleMouseY(screenY)
        val point2i = lastPointerPositionMap[pointer]!!
        componentList
                .filter { it.isEnabled && it.isShown }
                .forEach { it.clickDragged(mouseX, mouseY, point2i.x, point2i.y, pointer) }
        pointerDragged(mouseX, mouseY, point2i.x, point2i.y, pointer)
        lastPointerPositionMap.put(pointer, Point2f(mouseX, mouseY))
        return false
    }

    /**
     * Called by [Gui.touchDragged] when the user moves his pointer over the screen while pressing it, used scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun pointerDragged(mouseX: Float, mouseY: Float, prevMouseX: Float, prevMouseY: Float, pointer: Int) {

    }

    override final fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX(screenX)
        val mouseY = scaleMouseY(screenY)
        val point2i = lastPointerPositionMap[0]!!
        componentList
                .filter { it.isEnabled && it.isShown }
                .forEach { it.mouseMoved(mouseX, mouseY, point2i.x, point2i.y) }
        pointerMoved(mouseX, mouseY, point2i.x, point2i.y)
        lastPointerPositionMap.put(0, Point2f(mouseX, mouseY))
        return false
    }

    /**
     * Called by [Gui.mouseMoved] when the user moves his pointer over the screen without pressing it, used scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun pointerMoved(mouseX: Float, mouseY: Float, prevMouseX: Float, prevMouseY: Float) {

    }

    override final fun scrolled(amount: Int): Boolean {
        if (!allowInput) return false
        val mouseX = scaleMouseX()
        val mouseY = scaleMouseY()

        componentList
                .filter { it.isEnabled && it.isShown }
                .forEach { it.scroll(mouseX, mouseY, amount) }

        scrolledWheel(mouseX, mouseY, amount)
        return false
    }

    /**
     * Called by [Gui.scrolled] when the user scrolls his mouse wheel, used scaled mouse amounts

     * @see scaleMouseX
     * @see scaleMouseY
     */
    open fun scrolledWheel(mouseX: Float, mouseY: Float, amount: Int) {

    }

    open fun registerComponent(guiComponent: GuiComponent) {
        guiComponent.setCurrentGui(this)
        componentList.add(guiComponent)
    }

    open fun removeGuiComponent(component: GuiComponent) {
        componentsToBeRemoved.add(component)
        component.setCurrentGui(null)
    }

    /**
     * @return whether the given point hovers a gui component
     */
    open fun hoversComponent(mouseX: Float, mouseY: Float): Boolean {
        return componentList.any { it.isHovered(mouseX, mouseY) && it.isShown }
    }

    open fun exitGui() {}

    override fun buttonCallback(button: GuiButton) {

    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean = false

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        for (guiComponent in componentList) {
            guiComponent.onFling(velocityX, velocityY, button)
        }
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean = false

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean = false

    override fun pinchStop() {

    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean = false

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean = false

    override fun longPress(x: Float, y: Float): Boolean = false

    override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?): Boolean = false
}
