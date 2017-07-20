package de.intektor.vtable.client.renderer.entity.renderers

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import de.intektor.vtable.client.renderer.entity.IEntityRenderer
import de.intektor.vtable.common.world.World
import de.intektor.vtable.common.world.entity.schafkopfn.EntityCard
import de.intektor.vtable.util.morph

/**
 * @author Intektor
 */
class EntityCardRenderer : IEntityRenderer<EntityCard> {

    val model: Model

    init {
        val modelBuilder = ModelBuilder()
        model = modelBuilder.createBox(9f, 0.2f, 6f, Material(TextureAttribute.createDiffuse(Texture("schafkopfn/eichel_ober.png"))), (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal or VertexAttributes.Usage.TextureCoordinates).toLong())
    }

    override fun renderEntity(entity: EntityCard, world: World, batch: ModelBatch, partialTicks: Float, spriteBatch: SpriteBatch, camera: PerspectiveCamera) {
        val modelInstance = ModelInstance(model)
        modelInstance.transform.set(entity.motionState.transform.basis.morph())
        modelInstance.transform.setToTranslation(entity.motionState.transform.origin.morph())
        batch.begin(camera)
        batch.render(modelInstance)
        batch.end()
    }

}