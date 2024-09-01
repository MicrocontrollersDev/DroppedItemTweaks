package dev.microcontrollers.droppeditemtweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.microcontrollers.droppeditemtweaks.config.DroppedItemTweaksConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
//#if MC <= 1.20.4
//$$ import net.minecraft.item.ItemStack;
//#endif
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
    @ModifyExpressionValue(method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;sin(F)F"))
    private float makeItemStatic(float value) {
        return DroppedItemTweaksConfig.CONFIG.instance().staticItems ? -1.0f : value;
    }

    @Inject(method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V"))
    private void scaleItems(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        ItemStack stack = itemEntity.getStack();
        float scale = DroppedItemTweaksConfig.CONFIG.instance().itemScale;
        if (DroppedItemTweaksConfig.CONFIG.instance().uhcOverlay != 0F
                && (stack.isOf(Items.APPLE)
                || stack.isOf(Items.GOLDEN_APPLE)
                || stack.isOf(Items.GOLD_INGOT)
                || stack.isOf(Items.GOLD_NUGGET)
                || stack.isOf(Items.PLAYER_HEAD)))
           scale = DroppedItemTweaksConfig.CONFIG.instance().uhcOverlay;
        matrixStack.scale(scale, scale, scale);
    }

    @ModifyReturnValue(method = "getRenderedAmount", at = @At("RETURN"))
    //#if MC >= 1.20.6
    private static int forceStackAmount(int original, int stackSize) {
        return DroppedItemTweaksConfig.CONFIG.instance().dropStackCount != 0 ? Math.min(DroppedItemTweaksConfig.CONFIG.instance().dropStackCount, stackSize) : original;
    //#else
    //$$ private int forceStackAmount(int original, ItemStack stack) {
    //$$    return DroppedItemTweaksConfig.CONFIG.instance().dropStackCount != 0 ? Math.min(DroppedItemTweaksConfig.CONFIG.instance().dropStackCount, stack.getCount()) : original;
    //#endif
    }
}
