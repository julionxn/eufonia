package me.julionxn.jueguitosclient.games.lights;

import me.julionxn.jueguitosclient.core.BasicClientMinigame;
import me.julionxn.jueguitosclient.games.lights.util.Allowed;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class LightsClientGame extends BasicClientMinigame {

    @Override
    public String getId() {
        return "lights";
    }

    @Override
    protected void onStart(PacketByteBuf buf) {
        setAllowed(true);
    }

    @Override
    protected void onStop(PacketByteBuf buf) {
        setAllowed(false);
    }

    private void setAllowed(boolean allowed){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        PlayerEntity player = client.player;
        if (player == null) return;
        ((Allowed) player).eufonia$setAllowed(allowed);
    }

    @Override
    protected void onReset(PacketByteBuf buf) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        PlayerEntity player = client.player;
        if (player == null) return;
        Allowed allowed = (Allowed) player;
        allowed.eufonia$setAllowed(true);
        allowed.eufonia$shouldHandle(true);
    }

}
