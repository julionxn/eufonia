package me.julionxn.jueguitosclient.games.freezers.mixin;

import me.julionxn.jueguitosclient.games.freezers.util.Freeze;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements Freeze {

    @Unique
    private boolean freezed;

    @Override
    public void eufonia$setFreeze(boolean state) {
        this.freezed = state;
    }

    @Override
    public boolean eufonia$isFreezed() {
        return freezed;
    }

}
