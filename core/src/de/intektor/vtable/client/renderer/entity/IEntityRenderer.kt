package de.intektor.vtable.client.renderer.entity

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.ModelBatch
import de.intektor.vtable.common.world.World
import de.intektor.vtable.common.world.entity.Entity

/**
 * @author Intektor
 */
interface IEntityRenderer<in T : Entity> {

    fun renderEntity(entity: T, world: World, batch: ModelBatch, partialTicks: Float, spriteBatch: SpriteBatch, camera: PerspectiveCamera)
}