package dev.microcontrollers.droppeditemtweaks.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.ColorController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class DroppedItemTweaksConfig {
    public static final ConfigClassHandler<DroppedItemTweaksConfig> CONFIG = ConfigClassHandler.createBuilder(DroppedItemTweaksConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("droppeditemtweaks.json"))
                    .build())
            .build();

    @SerialEntry public boolean staticItems = false;
    @SerialEntry public int dropStackCount = 0;

    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.literal("Render Tweaks"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Render Tweaks"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Static Dropped Items"))
                                .description(OptionDescription.of(Text.of("Remove the bobbing of items dropped on the ground.")))
                                .binding(defaults.staticItems, () -> config.staticItems, newVal -> config.staticItems = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.literal("Dropped Stack Item Count"))
                                .description(OptionDescription.of(Text.of("The max amount of dropped items to render. This does not scale like vanilla. Set to 0 to disable.")))
                                .binding(0, () -> config.dropStackCount, newVal -> config.dropStackCount = newVal)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0, 64)
                                        .step(1))
                                .build())
                        .build())
        )).generateScreen(parent);
    }
}
