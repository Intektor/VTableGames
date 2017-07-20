package de.intektor.vtable.client.guis

import de.intektor.vtable.GameClient
import de.intektor.vtable.client.gui.Gui
import de.intektor.vtable.client.network.ClientConnection
import de.intektor.vtable.common.GameServer

/**
 * @author Intektor
 */
class GuiStartGame : Gui() {

    init {
        GameServer.launch()
    }

    override fun updateGui(mouseX: Float, mouseY: Float) {
        super.updateGui(mouseX, mouseY)
        if (GameServer.isReadyForConnections()) {
            ClientConnection.connect("localhost", 19943)
            GameClient.showGui(GuiPlay())
        }
    }

}