package me.julionxn.jueguitosclient.games.freezers;

import me.julionxn.jueguitosclient.core.BasicClientMinigame;
import me.julionxn.jueguitosclient.core.ClientMinigame;
import me.julionxn.jueguitosclient.core.timer.BasicTimer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class FreezersClientGame extends BasicClientMinigame {

    @Override
    public String getId() {
        return "freezers";
    }

    @Override
    public void onPlayerMove(PlayerEntity player) {

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
}
