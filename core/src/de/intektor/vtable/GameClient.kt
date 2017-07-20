package de.intektor.vtable

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import de.intektor.vtable.client.gui.Gui
import de.intektor.vtable.client.guis.GuiStartGame
import de.intektor.vtable.client.network.ClientConnection
import de.intektor.vtable.client.network.handler.*
import de.intektor.vtable.common.CommonCode
import de.intektor.vtable.common.GameServer
import de.intektor.vtable.common.network.INetwork
import de.intektor.vtable.common.network.packets.server_to_client.*
import java.util.concurrent.LinkedBlockingQueue

object GameClient : ApplicationAdapter() {

    lateinit var network: INetwork

    lateinit var defaultSpriteBatch: SpriteBatch
    lateinit var defaultShapeRenderer: ShapeRenderer
    lateinit var defaultModelBatch: ModelBatch

    lateinit var viewport: Viewport
    lateinit var camera: OrthographicCamera

    val preferredScreenWidth: Float = 1920f
    val preferredScreenHeight: Float = 1080f

    var currentGui: Gui? = null

    private val scheduledTasks = LinkedBlockingQueue<Runnable>()

    var partialTicks: Float = 0f

    private var lastTickTime: Long = 0
    private var totalTickCount: Long = 0

    override fun create() {
        defaultSpriteBatch = SpriteBatch()
        defaultShapeRenderer = ShapeRenderer()
        defaultModelBatch = ModelBatch()
        camera = OrthographicCamera(preferredScreenWidth, preferredScreenHeight)
        viewport = ScalingViewport(Scaling.fill, preferredScreenWidth, preferredScreenHeight, camera)
        viewport.apply()
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
        camera.update()
        defaultSpriteBatch.projectionMatrix = camera.combined
        defaultShapeRenderer.projectionMatrix = camera.combined
        defaultShapeRenderer.setAutoShapeType(true)

        registerPacketHandlers()

        showGui(GuiStartGame())
    }

    override fun render() {
        super.render()
        if (System.nanoTime() - lastTickTime >= 31500000f) {
            lastTickTime = System.nanoTime()
            totalTickCount++
            updateGame()
        }
        renderGame()
    }

    fun renderGame() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        camera.update()
        defaultShapeRenderer.projectionMatrix = camera.combined
        defaultSpriteBatch.projectionMatrix = camera.combined
        partialTicks = (System.nanoTime() - lastTickTime) / (31.25f * 1000000000.0f)
        if (currentGui != null) currentGui!!.render(Gdx.input.x, Gdx.input.y, camera)
    }

    fun updateGame() {
        while (true) {
            val r = scheduledTasks.poll() ?: break
            r.run()
        }
        if (currentGui != null) currentGui!!.update(Gdx.input.x, Gdx.input.y)
    }

    fun addScheduledTask(runnable: Runnable) {
        scheduledTasks.add(runnable)
    }

    override fun dispose() {
        ClientConnection.disconnect()
        GameServer.dispose()
    }

    fun registerPacketHandlers() {
        CommonCode.packetRegistry.registerHandler(KickPacketToClient::class.java, KickPacketToClientHandler())
        CommonCode.packetRegistry.registerHandler(SpawnEntityPacketToClient::class.java, SpawnEntityPacketToClientHandler())
        CommonCode.packetRegistry.registerHandler(ServerInfoPacketToClient::class.java, ServerInfoPacketToClientHandler())
        CommonCode.packetRegistry.registerHandler(UpdateCursorPositionPacketToClient::class.java, UpdateCursorPositionPacketToClientHandler())
        CommonCode.packetRegistry.registerHandler(EntityUpdatePacketToClient::class.java, EntityUpdatePacketToClientHandler())
        CommonCode.packetRegistry.registerHandler(PickupObjectPacketToClient::class.java, PickupObjectPacketToClientHandler())
        CommonCode.packetRegistry.registerHandler(DropObjectPacketToClient::class.java, DropObjectPacketToClientHandler())
    }

    fun showGui(gui: Gui) {
        if (currentGui != null) currentGui!!.exitGui()
        currentGui = gui
    }
}
