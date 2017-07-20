package de.intektor.vtable.client.gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.intektor.vtable.client.gui.util.getTextHeight
import de.intektor.vtable.client.gui.util.getTextWidth

/**
 * @author Intektor
 */
open class ClientRenderer {

    fun drawString(spriteBatch: SpriteBatch, font: BitmapFont, text: String, x: Float, y: Float, color: Color, centerX: Boolean = false, centerY: Boolean = false) {
        var drawX = x
        var drawY = x
        if (centerX) drawX += text.getTextWidth(font) / 2f
        if (centerY) drawY += text.getTextHeight(font) / 2f
        spriteBatch.color = color
        font.draw(spriteBatch, text, drawX, drawY)
    }

    fun enableBlending() {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    }

    fun disableBlending() {
        Gdx.gl.glDisable(GL20.GL_BLEND)
    }
}