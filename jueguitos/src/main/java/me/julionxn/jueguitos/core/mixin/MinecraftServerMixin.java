package me.julionxn.jueguitos.core.mixin;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci){
        if (!shouldKeepTicking.getAsBoolean()) return;
        Minigame minigame = GameStateManager.getInstance().getActiveMinigame();
        if (minigame == null || !minigame.isPlaying()) return;
        minigame.tick();
    }

}
