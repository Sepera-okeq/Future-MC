package thedarkcolour.futuremc.entity.fish.tropical

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity
import kotlin.math.PI
import kotlin.math.sin

class ModelTropicalFishA(scale: Float = 0F) : ModelBase() {
    private val body: ModelRenderer
    private val tail: ModelRenderer
    private val finRight: ModelRenderer
    private val finLeft: ModelRenderer
    private val finTop: ModelRenderer

    init {
        textureWidth = 32
        body = ModelRenderer(this, 0, 0)
        body.addBox(-1.0F, -1.5F, -3.0F, 2, 3, 6, scale)
        body.setRotationPoint(0.0F, 22.0F, 0.0F)
        tail = ModelRenderer(this, 22, -6)
        tail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 6, scale)
        tail.setRotationPoint(0.0F, 22.0F, 3.0F)
        finRight = ModelRenderer(this, 2, 16)
        finRight.addBox(-2.0F, -1.0F, 0.0F, 2, 2, 0, scale)
        finRight.setRotationPoint(-1.0F, 22.5F, 0.0F)
        finRight.rotateAngleY = PI.toFloat() / 4F
        finLeft = ModelRenderer(this, 2, 12)
        finLeft.addBox(0.0F, -1.0F, 0.0F, 2, 2, 0, scale)
        finLeft.setRotationPoint(1.0F, 22.5F, 0.0F)
        finLeft.rotateAngleY = (-PI).toFloat() / 4F
        finTop = ModelRenderer(this, 10, -5)
        finTop.addBox(0.0F, -3.0F, 0.0F, 0, 3, 6, scale)
        finTop.setRotationPoint(0.0F, 20.5F, -3.0F)
    }

    override fun render(entityIn: Entity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn)
        body.render(scale)
        tail.render(scale)
        finRight.render(scale)
        finLeft.render(scale)
        finTop.render(scale)
    }

    override fun setRotationAngles(limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float, entityIn: Entity) {
        val f = if (entityIn.isInWater) {
            1F
        } else {
            1.5F
        }
        tail.rotateAngleY = -f * 0.45F * sin(0.6F * ageInTicks)
    }
}