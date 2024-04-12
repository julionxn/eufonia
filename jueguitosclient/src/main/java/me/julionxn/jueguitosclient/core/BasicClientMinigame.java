package me.julionxn.jueguitosclient.core;

import me.julionxn.jueguitosclient.core.timer.BasicTimer;
import net.minecraft.network.PacketByteBuf;

public abstract class BasicClientMinigame implements ClientMinigame {

    private boolean running;
    protected final BasicTimer timer = new BasicTimer(-1, -1);

    @Override
    public void start(PacketByteBuf buf) {
        running = true;
        onStart(buf);
    }

    protected abstract void onStart(PacketByteBuf buf);

    @Override
    public void stop(PacketByteBuf buf) {
        running = false;
        onStop(buf);
    }

    protected abstract void onStop(PacketByteBuf buf);

    @Override
    public void reset(PacketByteBuf buf) {
        running = false;
        onReset(buf);
    }

    protected abstract void onReset(PacketByteBuf buf);

    @Override
    public BasicTimer getTimer() {
        return timer;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

}
