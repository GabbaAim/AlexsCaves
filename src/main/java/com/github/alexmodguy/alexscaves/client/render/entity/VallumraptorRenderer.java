package com.github.alexmodguy.alexscaves.client.render.entity;

import com.github.alexmodguy.alexscaves.client.model.VallumraptorModel;
import com.github.alexmodguy.alexscaves.server.entity.living.VallumraptorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class VallumraptorRenderer extends MobRenderer<VallumraptorEntity, VallumraptorModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("alexscaves:textures/entity/vallumraptor.png");
    private static final ResourceLocation TEXTURE_ELDER = new ResourceLocation("alexscaves:textures/entity/vallumraptor_elder.png");

    public VallumraptorRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new VallumraptorModel(), 0.3F);
        this.addLayer(new ItemLayer());
    }

    protected void scale(VallumraptorEntity mob, PoseStack matrixStackIn, float partialTicks) {
        if(mob.isElder()){
            matrixStackIn.scale(1.1F, 1.1F, 1.1F);
        }
    }

    public ResourceLocation getTextureLocation(VallumraptorEntity entity) {
        return entity.isElder() ? TEXTURE_ELDER : TEXTURE;
    }

    class ItemLayer extends RenderLayer<VallumraptorEntity, VallumraptorModel> {

        public ItemLayer() {
            super(VallumraptorRenderer.this);
        }

        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, VallumraptorEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ItemStack itemstack = entitylivingbaseIn.getMainHandItem();
            if(!itemstack.isEmpty()){
                boolean left = entitylivingbaseIn.isLeftHanded();
                matrixStackIn.pushPose();
                if(entitylivingbaseIn.isBaby()){
                    matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                    matrixStackIn.translate(0.0D, 1.5D, 0D);
                }
                matrixStackIn.pushPose();
                getParentModel().translateToHand(matrixStackIn, left);
                if(entitylivingbaseIn.isBaby()){
                    matrixStackIn.translate(0.0D, 0.1F, -0.6D);
                }
                matrixStackIn.translate(left ? -0.2F : 0.2F, 0.2F, -0.3F);
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180));
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-10));
                ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
                renderer.renderItem(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.popPose();
                matrixStackIn.popPose();
            }
        }
    }
}

