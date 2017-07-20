package de.intektor.vtable.client.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import de.intektor.vtable.client.renderer.entity.EntityRenderRegistry
import de.intektor.vtable.common.world.World

/**
 * @author Intektor
 */
object WorldRenderer {

    val environment: Environment = Environment()

    init {
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        environment.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f))
    }

    fun renderWorld(world: World, partialTicks: Float, camera: PerspectiveCamera, batch: ModelBatch, spriteBatch: SpriteBatch) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)

        for (entity in world.loadedEntityList) {
            EntityRenderRegistry.getRenderer(entity.javaClass).renderEntity(entity, world, batch, partialTicks, spriteBatch, camera)
        }
    }
}
