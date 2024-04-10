package me.julionxn.jueguitosclient.core;

import me.julionxn.jueguitosclient.core.timer.BasicTimer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public interface ClientMinigame {

    String getId();
    void start(PacketByteBuf buf);
    void stop(PacketByteBuf buf);
    void reset(PacketByteBuf buf);
    default void tick(){}
    boolean isRunning();
    BasicTimer getTimer();
    void onPlayerMove(PlayerEntity player);

}
