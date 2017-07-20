package de.intektor.vtable.common

import com.badlogic.gdx.utils.Disposable
import de.intektor.vtable.GameClient
import de.intektor.vtable.common.network.*
import de.intektor.vtable.common.network.packets.client_to_server.RegisterPacketToServer
import de.intektor.vtable.common.network.packets.server_to_client.ServerInfoPacketToClient
import de.intektor.vtable.common.network.packets.server_to_client.SpawnEntityPacketToClient
import de.intektor.vtable.common.player.Player
import de.intektor.vtable.common.world.WorldServer
import java.io.DataInputStream
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue
import kotlin.collections.HashMap

/**
 * @author Intektor
 */
object GameServer : Disposable {

    private @Volatile var serverSocket: IServerSocket? = null
    private @Volatile var serverRunning = false
    private val connectedClients = Collections.newSetFromMap(ConcurrentHashMap<ISocket, Boolean>())
    val playerMap: HashMap<ISocket, Player> = HashMap()

    lateinit var mainThread: MainThread

    fun launch() {
        launch(19943)
    }

    fun launch(port: Int) {
        object : Thread() {
            override fun run() {
                serverRunning = true
                serverSocket = GameClient.network.buildServerSocket(port, NetworkType.TCP)

                while (serverRunning) {
                    val clientSocket = serverSocket!!.accept()

                    object : Thread() {
                        override fun run() {
                            val input = DataInputStream(clientSocket.getInputStream())
                            while (clientSocket.isConnected()) {
                                readAndHandlePacket(input, CommonCode.packetRegistry, NetworkSide.SERVER, clientSocket)
                            }
                        }
                    }.start()
                }
            }
        }.start()

        mainThread = MainThread(this)
        mainThread.start()
    }

    fun registerAndInitClient(socket: ISocket, registerPacket: RegisterPacketToServer) {
        connectedClients.add(socket)
        playerMap.put(socket, Player(registerPacket.username, UUID.randomUUID()))
        mainThread.initClient(socket)
    }

    override fun dispose() {
        serverSocket?.close()
    }

    fun isReadyForConnections(): Boolean = serverSocket?.isBound() ?: false

    fun broadcast(packet: IPacket) {
        for (connectedClient in connectedClients) {
            packet.sendTo(connectedClient, CommonCode.packetRegistry)
        }
    }

    class MainThread(val server: GameServer) : Thread() {

        val world: WorldServer = WorldServer(server)

        private val scheduledTasks = LinkedBlockingQueue<Runnable>()

        override fun run() {
            var lastTickTime: Long
            while (server.serverRunning) {
                lastTickTime = System.nanoTime()
                gameTick()
                Thread.sleep(Math.max(((lastTickTime + 31500000L) - System.nanoTime()) / 1000000L, 0L))
            }
        }

        fun gameTick() {
            while (true) {
                val r = scheduledTasks.poll() ?: break
                r.run()
            }
            world.updateWorld()
        }

        fun initClient(socket: ISocket) {
            ServerInfoPacketToClient(playerMap.values.toList()).sendTo(socket, CommonCode.packetRegistry)
            for (entity in world.loadedEntityList) {
                SpawnEntityPacketToClient(entity).sendTo(socket, CommonCode.packetRegistry)
            }
        }

        fun addScheduledTask(runnable: Runnable) {
            scheduledTasks.add(runnable)
        }
    }
}