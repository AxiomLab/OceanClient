package de.axiomlab.oceanclient.client.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.equipment.EquipmentModelLoader;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import de.axiomlab.oceanclient.client.database.DatabaseManager;
import java.util.HashMap;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.Dilation;

import net.minecraft.client.model.ModelTransform;

import java.util.Map;
import java.util.HashMap;






public class CapeRenderer {
    private static final Identifier STANDARD_CAPE = Identifier.of("oceanclient", "textures/capes/standard.png");

    public static void init() {
        // Use the vanilla cape layer identifier with Identifier.of
        EntityModelLayer CAPE_LAYER = new EntityModelLayer(Identifier.of("minecraft", "player"), "cape");

        // Create the cape model data
        TexturedModelData capeModelData = TexturedModelData.of(createCapeModel(), 64, 32);

        // Register the cape model layer
        EntityModelLayerRegistry.registerModelLayer(CAPE_LAYER, () -> capeModelData);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer playerRenderer) {
                Map<EntityModelLayer, TexturedModelData> modelMap = new HashMap<>();
                modelMap.put(CAPE_LAYER, capeModelData);
                LoadedEntityModels models = new LoadedEntityModels(modelMap);
                registrationHelper.register(new CustomCapeFeatureRenderer(playerRenderer, models, new EquipmentModelLoader()));
            }
        });
    }

    private static ModelData createCapeModel() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        root.addChild("cape", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return modelData;
    }



    private static class CustomCapeFeatureRenderer extends CapeFeatureRenderer {
        public CustomCapeFeatureRenderer(PlayerEntityRenderer context, LoadedEntityModels models, EquipmentModelLoader loader) {
            super(context, models, loader);
        }

        public void renderCape(DrawContext context, AbstractClientPlayerEntity player) {
            String uuid = player.getUuidAsString();
            Identifier capeTexture = STANDARD_CAPE;

            String equippedCapeTexture = DatabaseManager.getEquippedCapeTexture(uuid);
            if (equippedCapeTexture != null) {
                capeTexture = Identifier.of("oceanclient", "textures/capes/" + equippedCapeTexture);
            }

            RenderSystem.setShaderTexture(0, capeTexture);
            context.drawTexture(RenderLayer::getEntityCutout, capeTexture, 0, 0, 0.0F, 0.0F, 64, 32, 64, 32);

        }
    }
}
