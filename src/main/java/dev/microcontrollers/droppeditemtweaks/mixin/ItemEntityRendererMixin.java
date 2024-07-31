package dev.microcontrollers.droppeditemtweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.microcontrollers.droppeditemtweaks.config.DroppedItemTweaksConfig;
import net.minecraft.client.render.entity.ItemEntityRenderer;
//#if MC <= 1.20.4
//$$ import net.minecraft.item.ItemStack;
//#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
    @ModifyExpressionValue(method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;sin(F)F"))
    private float makeItemStatic(float value) {
        return DroppedItemTweaksConfig.CONFIG.instance().staticItems ? -1.0f : value;
    }

    @ModifyReturnValue(method = "getRenderedAmount", at = @At("RETURN"))
    //#if MC >= 1.20.6
    private static int forceStackAmount(int original, int stackSize) {
        return Math.min(DroppedItemTweaksConfig.CONFIG.instance().dropStackCount, stackSize);
        //#else
        //$$ private int forceStackAmount(int original, ItemStack stack) {
        //$$    return Math.min(DroppedItemTweaksConfig.CONFIG.instance().dropStackCount, stack.getCount());
        //#endif
    }
}
