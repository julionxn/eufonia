package me.julionxn.jueguitosclient.games.lights.mixin;

import me.julionxn.jueguitosclient.games.lights.networking.packets.C2S_NotAllowedMovement;
import me.julionxn.jueguitosclient.games.lights.util.Allowed;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {

    @Shadow @Final private GameOptions settings;

    @Inject(method = "tick", at = @At("HEAD"))
    private void update(boolean slowDown, float f, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        PlayerEntity player = client.player;
        if (player == null) return;
        Allowed allowed = (Allowed) player;
        if (allowed.eufonia$handle() && !allowed.eufonia$allowed()){
            if (settings.forwardKey.isPressed() || settings.backKey.isPressed() ||
            settings.leftKey.isPressed() || settings.rightKey.isPressed() ||
            settings.jumpKey.isPressed() || settings.sneakKey.isPressed()){
                C2S_NotAllowedMovement.send();
            }
        }
    }

}
