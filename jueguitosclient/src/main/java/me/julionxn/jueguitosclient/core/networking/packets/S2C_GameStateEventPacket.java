package me.julionxn.jueguitosclient.core.networking.packets;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.ClientMinigame;
import me.julionxn.jueguitosclient.core.managers.ClientGameStateManager;
import me.julionxn.jueguitosclient.core.networking.S2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class S2C_GameStateEventPacket implements S2CPacket {

    private static final Identifier ID = new Identifier(JueguitosClient.ID, "game_state_event");
    public static final int START_EVENT = 0;
    public static final int STOP_EVENT = 1;
    public static final int RESET_EVENT = 2;

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void handlePacket(MinecraftClient client, PacketByteBuf buf) {
        String gameId = buf.readString();
        int event = buf.readInt();
        long lastTime = buf.readLong();
        long remaining = buf.readLong();
        client.execute(() -> {
            if (event < 0){
                ClientGameStateManager.getInstance().setActiveMinigame(gameId);
                return;
            }
            ClientMinigame minigame = ClientGameStateManager.getInstance().getActiveMinigame();
            if (minigame == null) return;
            switch (event){
                case START_EVENT -> {
                    minigame.getTimer().run(lastTime, remaining);
                    minigame.start(buf);
                }
                case STOP_EVENT -> {
                    minigame.getTimer().stop(lastTime, remaining);
                    minigame.stop(buf);
                }
                case RESET_EVENT -> {
                    minigame.getTimer().stop(-1, -1);
                    minigame.reset(buf);
                }
            }
        });
    }

}
