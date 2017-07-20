package de.intektor.vtable.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.intektor.vtable.GameClient;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        GameClient.network = new DesktopNetworkImpl();
        new LwjglApplication(GameClient.INSTANCE, config);
    }
}
