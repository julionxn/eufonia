package me.julionxn.jueguitosclient.games.freezers.networking.packets;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.networking.C2SPacket;
import net.minecraft.util.Identifier;

public class C2S_SyncFreezeStatePacket implements C2SPacket {

    private static final Identifier ID = new Identifier(JueguitosClient.ID, "sync_freeze_state");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }
}
