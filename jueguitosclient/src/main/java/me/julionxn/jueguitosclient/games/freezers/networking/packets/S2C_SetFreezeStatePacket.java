package me.julionxn.jueguitosclient.games.freezers.networking.packets;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.networking.S2CPacket;
import me.julionxn.jueguitosclient.games.freezers.util.Freeze;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class S2C_SetFreezeStatePacket implements S2CPacket {

    private static final Identifier ID = new Identifier(JueguitosClient.ID, "set_freeze_state");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void handlePacket(MinecraftClient client, PacketByteBuf buf) {
        boolean state = buf.readBoolean();
        client.execute(() -> {
            PlayerEntity player = client.player;
            if (player == null) return;
            ((Freeze) player).eufonia$setFreeze(state);
        });
    }

}
