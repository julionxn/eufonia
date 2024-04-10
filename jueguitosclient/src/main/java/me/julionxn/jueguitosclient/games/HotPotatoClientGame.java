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
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.sendMessage(Text.of("CLIENT_START: " + timer.getRemainingSeconds()));
    }

    @Override
    protected void onStop(PacketByteBuf buf) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.sendMessage(Text.of("CLIENT_STOP: " + timer.getRemainingSeconds()));
    }

    @Override
    protected void onReset(PacketByteBuf buf) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.sendMessage(Text.of("CLIENT_RESET: " + timer.getRemainingSeconds()));
    }

    @Override
    public void onPlayerMove(PlayerEntity player) {

    }

}
