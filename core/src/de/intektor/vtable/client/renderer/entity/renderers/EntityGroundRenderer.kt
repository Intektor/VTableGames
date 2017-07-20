package de.intektor.vtable.client.renderer.entity.renderers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import de.intektor.vtable.client.renderer.entity.IEntityRenderer
import de.intektor.vtable.common.world.World
import de.intektor.vtable.common.world.entity.EntityGround
import de.intektor.vtable.util.morph

/**
 * @author Intektor
 */
class EntityGroundRenderer : IEntityRenderer<EntityGround> {

    val groundModel: Model

    init {
        val modelBuilder = ModelBuilder()
        groundModel = modelBuilder.createBox(100f, 5f, 100f, Material(ColorAttribute.createDiffuse(Color(0f, 0.4f, 0f, 1f))), (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong())
    }

    override fun renderEntity(entity: EntityGround, world: World, batch: ModelBatch, partialTicks: Float, spriteBatch: SpriteBatch, camera: PerspectiveCamera) {
        val modelInstance = ModelInstance(groundModel)
        modelInstance.transform.set(entity.motionState.transform.basis.morph())
        modelInstance.transform.setToTranslation(entity.motionState.transform.origin.morph())
        batch.begin(camera)
        batch.render(modelInstance)
        batch.end()
    }

}