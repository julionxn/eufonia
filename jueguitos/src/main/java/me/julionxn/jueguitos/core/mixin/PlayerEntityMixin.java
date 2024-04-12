package me.julionxn.jueguitos.core.mixin;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {


	@Inject(at = @At("HEAD"), method = "attack")
	private void attack(Entity target, CallbackInfo ci) {
		Minigame minigame = GameStateManager.getInstance().getActiveMinigame();
		if (minigame == null || !minigame.isPlaying()) return;
		if (target instanceof PlayerEntity damaged){
			minigame.onPlayerHitAnother((PlayerEntity)(Object)this, damaged);
		}
	}


}