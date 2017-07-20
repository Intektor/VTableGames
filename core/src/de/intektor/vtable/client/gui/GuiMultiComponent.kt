package de.intektor.vtable.client.gui

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import de.intektor.vtable.client.gui.components.GuiButton
import de.intektor.vtable.client.gui.util.isPointInRegion
import java.util.*

/**
 * @author Intektor
 */
open class GuiMultiComponent(x: Int, y: Int, width: Int, height: Int) : GuiComponent(x, y, width, height), GuiButton.GuiButtonCallback {

    protected var componentList: MutableList<GuiComponent> = ArrayList()
    private val componentsToBeAdded = ArrayList<GuiComponent>()
    private val componentsToBeRemoved = ArrayList<GuiComponent>()

    override fun renderComponent(drawX: Float, drawY: Float, mouseX: Float, mouseY: Float, camera: OrthographicCamera, sR: ShapeRenderer, sB: SpriteBatch) {
        super.renderComponent(drawX, drawY, mouseX, mouseY, camera, sR, sB)
        renderSubComponents(drawX, drawY, mouseX, mouseY, camera, sR, sB)
    }

    fun renderSubComponents(drawX: Float, drawY: Float, mouseX: Float, mouseY: Float, camera: OrthographicCamera, sR: ShapeRenderer, sB: SpriteBatch) {
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        componentList
                .filter { it.isShown }
                .forEach { it.renderComponent(drawX + it.x, drawY + it.y, lMouseX, lMouseY, camera, sR, sB) }
    }

    override fun updateComponent(mouseX: Float, mouseY: Float, drawX: Float, drawY: Float) {
        super.updateComponent(mouseX, mouseY, drawX, drawY)
        componentList.removeAll(componentsToBeRemoved)
        componentsToBeRemoved.clear()
        componentList.addAll(componentsToBeAdded)
        componentsToBeAdded.clear()
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.updateComponent(lMouseX, lMouseY, drawX + guiComponent.x, drawY + guiComponent.y)
        }
    }

    override fun clickDown(mouseX: Float, mouseY: Float, pointer: Int, button: Int, drawX: Float, drawY: Float) {
        super.clickDown(mouseX, mouseY, pointer, button, drawX, drawY)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.clickDown(lMouseX, lMouseY, pointer, button, drawX + guiComponent.x, drawY + guiComponent.y)
        }
    }

    override fun clicked(mouseX: Float, mouseY: Float, pointer: Int) {
        super.clicked(mouseX, mouseY, pointer)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.clicked(lMouseX, lMouseY, pointer)
        }
    }

    override fun clickUp(mouseX: Float, mouseY: Float, pointer: Int, button: Int, drawX: Float, drawY: Float) {
        super.clickUp(mouseX, mouseY, pointer, button, drawX, drawY)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.clickUp(lMouseX, lMouseY, pointer, button, drawX + guiComponent.x, drawY + guiComponent.y)
        }
    }

    override fun keyDown(mouseX: Float, mouseY: Float, keyCode: Int) {
        super.keyDown(mouseX, mouseY, keyCode)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.keyDown(lMouseX, lMouseY, keyCode)
        }
    }

    override fun keyPressed(mouseX: Float, mouseY: Float, keyCode: Int) {
        super.keyPressed(mouseX, mouseY, keyCode)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.keyPressed(lMouseX, lMouseY, keyCode)
        }
    }

    override fun keyReleased(mouseX: Float, mouseY: Float, keyCode: Int) {
        super.keyReleased(mouseX, mouseY, keyCode)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.keyReleased(lMouseX, lMouseY, keyCode)
        }
    }

    override fun charTyped(mouseX: Float, mouseY: Float, character: Char) {
        super.charTyped(mouseX, mouseY, character)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.charTyped(lMouseX, lMouseY, character)
        }
    }

    override fun mouseMoved(mouseX: Float, mouseY: Float, prevX: Float, prevY: Float) {
        super.mouseMoved(mouseX, mouseY, prevX, prevY)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        val prevlmouseX = localMouseX(prevX)
        val prevlmouseY = localMouseY(prevY)
        for (guiComponent in componentList) {
            guiComponent.mouseMoved(lMouseX, lMouseY, prevlmouseX, prevlmouseY)
        }
    }

    override fun clickDragged(mouseX: Float, mouseY: Float, prevX: Float, prevY: Float, pointer: Int) {
        super.clickDragged(mouseX, mouseY, prevX, prevY, pointer)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        val prevlmouseX = localMouseX(prevX)
        val prevlmouseY = localMouseY(prevY)
        for (guiComponent in componentList) {
            guiComponent.clickDragged(lMouseX, lMouseY, prevlmouseX, prevlmouseY, pointer)
        }
    }

    override fun onFling(velocityX: Float, velocityY: Float, button: Int) {
        super.onFling(velocityX, velocityY, button)
        for (guiComponent in componentList) {
            guiComponent.onFling(velocityX, velocityY, button)
        }
    }

    override fun scroll(mouseX: Float, mouseY: Float, scrollAmount: Int) {
        super.scroll(mouseX, mouseY, scrollAmount)
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        for (guiComponent in componentList) {
            guiComponent.scroll(lMouseX, lMouseY, scrollAmount)
        }
    }

    override fun isHovered(mouseX: Float, mouseY: Float): Boolean {
        val lMouseX = localMouseX(mouseX)
        val lMouseY = localMouseY(mouseY)
        return (componentList.any { it.isHovered(lMouseX, lMouseY) })
                || isPointInRegion(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), mouseX, mouseY)
    }

    /**
     * Registers a Gui Component to this GuiMultiComponent, moves the GuiComponent to register relative to this components position
     */
    protected fun registerComponent(guiComponent: GuiComponent) {
        if (guiComponent is GuiButton) {
            guiComponent.callback = this
        }
        componentsToBeAdded.add(guiComponent)
    }

    protected fun removeGuiComponent(guiComponent: GuiComponent) {
        componentsToBeRemoved.add(guiComponent)
    }

    override fun moveComponent(amountX: Int, amountY: Int) {
        super.moveComponent(amountX, amountY)
    }

    override fun setPosition(x: Int, y: Int) {
        moveComponent(x - this.x, y - this.y)
    }

    override fun buttonCallback(button: GuiButton) {

    }

    protected fun localMouseX(mouseX: Float): Float {
        return mouseX - x
    }

    protected fun localMouseY(mouseY: Float): Float {
        return mouseY - y
    }
}

