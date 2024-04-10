package me.julionxn.jueguitos.core.mixin;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import me.julionxn.jueguitos.core.util.PlayerTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerTags {

	@Unique
	private String teamTag = "";

	@Inject(at = @At("HEAD"), method = "attack")
	private void attack(Entity target, CallbackInfo ci) {
		Minigame minigame = GameStateManager.getInstance().getActiveMinigame();
		if (minigame == null || !minigame.isPlaying()) return;
		if (target instanceof PlayerEntity damaged){
			minigame.onPlayerHitAnother((PlayerEntity)(Object)this, damaged);
		}
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void writeNbt(NbtCompound nbt, CallbackInfo ci){
		nbt.putString("gamesTeam", teamTag);
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void readNbt(NbtCompound nbt, CallbackInfo ci){
		teamTag = nbt.getString("gamesTeam");
	}

	@Override
	public void eufonia$setTeamTag(String team) {
		this.teamTag = team;
	}

	@Override
	public String eufonia$getTeamTag() {
		return teamTag;
	}

}