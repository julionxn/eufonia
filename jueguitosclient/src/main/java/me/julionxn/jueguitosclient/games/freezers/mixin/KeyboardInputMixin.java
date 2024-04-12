package me.julionxn.jueguitosclient.games.freezers.mixin;

import me.julionxn.jueguitosclient.games.freezers.util.Freeze;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void move(boolean slowDown, float f, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        PlayerEntity player = client.player;
        if (player == null) return;
        if (((Freeze) player).eufonia$isFreezed()){
            ci.cancel();
        }
    }

}
