package dev.microcontrollers.droppeditemtweaks;

import dev.microcontrollers.droppeditemtweaks.config.DroppedItemTweaksConfig;
import net.fabricmc.api.ModInitializer;

public class DroppedItemTweaks implements ModInitializer {
	@Override
	public void onInitialize() {
		DroppedItemTweaksConfig.CONFIG.load();
	}
}
