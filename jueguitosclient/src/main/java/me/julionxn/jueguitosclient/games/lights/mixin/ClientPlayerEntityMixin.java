package me.julionxn.jueguitosclient.games.lights.mixin;

import com.mojang.authlib.GameProfile;
import me.julionxn.jueguitosclient.games.lights.networking.packets.C2S_NotAllowedMovement;
import me.julionxn.jueguitosclient.games.lights.util.Allowed;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity implements Allowed {

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Shadow private float lastYaw;
    @Shadow private float lastPitch;
    @Unique
    private boolean allowed = true;
    @Unique
    private boolean handling = true;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tick(CallbackInfo ci){
        if (handling && !allowed){
            if (getYaw() != lastYaw || getPitch() != lastPitch){
                C2S_NotAllowedMovement.send();
            }
        }
    }

    @Override
    public void eufonia$setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Override
    public boolean eufonia$allowed() {
        return allowed;
    }

    @Override
    public void eufonia$shouldHandle(boolean should){
        handling = should;
    }

    @Override
    public boolean eufonia$handle() {
        return handling;
    }


}
