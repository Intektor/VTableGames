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
import de.intektor.vtable.common.world.entity.test.EntityTestCube
import de.intektor.vtable.util.morph

/**
 * @author Intektor
 */
class EntityTestCubeRenderer : IEntityRenderer<EntityTestCube> {

    val cubeModel: Model

    init {
        val modelBuilder = ModelBuilder()
        cubeModel = modelBuilder.createBox(1f, 1f, 1f, Material(ColorAttribute.createDiffuse(Color.GREEN)), (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong())
    }

    override fun renderEntity(entity: EntityTestCube, world: World, batch: ModelBatch, partialTicks: Float, spriteBatch: SpriteBatch, camera: PerspectiveCamera) {
        val modelInstance = ModelInstance(cubeModel)
        modelInstance.transform.set(entity.motionState.transform.basis.morph())
        modelInstance.transform.setToTranslation(entity.motionState.transform.origin.morph())
        batch.begin(camera)
        batch.render(modelInstance)
        batch.end()
    }
}
