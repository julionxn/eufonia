package me.julionxn.jueguitosclient.games;

import me.julionxn.jueguitosclient.core.BasicClientMinigame;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class HotPotatoClientGame extends BasicClientMinigame {

    @Override
    public String getId() {
        return "hotpotato";
    }

    @Override
    protected void onStart(PacketByteBuf buf) {

    }

    @Override
    protected void onStop(PacketByteBuf buf) {

    }

    @Override
    protected void onReset(PacketByteBuf buf) {

    }

    @Override
    public void onPlayerMove(PlayerEntity player) {

    }

}
