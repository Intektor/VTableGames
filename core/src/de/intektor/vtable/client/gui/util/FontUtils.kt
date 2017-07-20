package de.intektor.vtable.client.gui.util

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout

/**
 * @author Intektor
 */

private val layout = GlyphLayout()

fun String.getTextWidth(font: BitmapFont): Float {
    layout.setText(font, this)
    return layout.width
}

fun String.getTextHeight(font: BitmapFont): Float {
    layout.setText(font, this)
    return layout.height
}