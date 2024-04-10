package me.julionxn.jueguitosclient.core.mixin;

import me.julionxn.jueguitosclient.core.ClientMinigame;
import me.julionxn.jueguitosclient.core.managers.ClientGameStateManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Inject(at = @At("TAIL"), method = "tick")
	private void init(CallbackInfo info) {
		ClientMinigame minigame = ClientGameStateManager.getInstance().getActiveMinigame();
		if (minigame == null || !minigame.isRunning()) return;
		minigame.tick();
	}
}