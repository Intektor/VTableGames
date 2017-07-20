package de.intektor.vtable.desktop;

import de.intektor.vtable.api.network.tcp.TcpServerSocket;
import de.intektor.vtable.api.network.tcp.TcpSocket;
import de.intektor.vtable.common.network.INetwork;
import de.intektor.vtable.common.network.IServerSocket;
import de.intektor.vtable.common.network.ISocket;
import de.intektor.vtable.common.network.NetworkType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Intektor
 */
public class DesktopNetworkImpl implements INetwork {

    @NotNull
    @Override
    public ISocket buildSocket(@NotNull String ip, int port, @NotNull NetworkType side) {
        return new TcpSocket(ip, port);
    }

    @NotNull
    @Override
    public IServerSocket buildServerSocket(int port, @NotNull NetworkType type) {
        return new TcpServerSocket(port);
    }
}
