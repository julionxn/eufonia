package me.julionxn.jueguitos.games.freezers.mixin;

import me.julionxn.jueguitos.games.freezers.util.Freeze;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements Freeze {

    @Unique
    private boolean freezed;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.putBoolean("freezed", freezed);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci){
        freezed = nbt.getBoolean("freezed");
    }


    @Override
    public void eufonia$setFreeze(boolean state) {
        this.freezed = state;
    }

    @Override
    public boolean eufonia$isFreezed() {
        return freezed;
    }
}
