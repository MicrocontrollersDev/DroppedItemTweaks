package dev.microcontrollers.droppeditemtweaks.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class DroppedItemTweaksConfig {
    public static final ConfigClassHandler<DroppedItemTweaksConfig> CONFIG = ConfigClassHandler.createBuilder(DroppedItemTweaksConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("droppeditemtweaks.json"))
                    .build())
            .build();

    @SerialEntry public boolean staticItems = false;
    @SerialEntry public int dropStackCount = 0;
    @SerialEntry public float itemScale = 1F;
    @SerialEntry public float uhcOverlay = 0F;


    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.translatable("dropped-item-tweaks.dropped-item-tweaks"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("dropped-item-tweaks.dropped-item-tweaks"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("dropped-item-tweaks.static-dropped-items"))
                                .description(OptionDescription.of(Text.translatable("dropped-item-tweaks.static-dropped-items.description")))
                                .binding(defaults.staticItems, () -> config.staticItems, newVal -> config.staticItems = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("dropped-item-tweaks.dropped-stack-item-count"))
                                .description(OptionDescription.of(Text.translatable("dropped-item-tweaks.dropped-stack-item-count.description")))
                                .binding(0, () -> config.dropStackCount, newVal -> config.dropStackCount = newVal)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0, 64)
                                        .step(1))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("dropped-item-tweaks.item-scale"))
                                .description(OptionDescription.of(Text.translatable("dropped-item-tweaks.item-scale.description")))
                                .binding(1F, () -> config.itemScale, newVal -> config.itemScale = newVal)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                        .range(0.1F, 2F)
                                        .step(0.1F))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("dropped-item-tweaks.uhc-overlay"))
                                .description(OptionDescription.of(Text.translatable("dropped-item-tweaks.uhc-overlay.description")))
                                .binding(0F, () -> config.uhcOverlay, newVal -> config.uhcOverlay = newVal)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                        .range(0F, 2F)
                                        .step(0.1F))
                                .build())
                        .build())
        )).generateScreen(parent);
    }
}
