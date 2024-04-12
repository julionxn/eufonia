package me.julionxn.jueguitos.games.freezers.networking.packets;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.networking.C2SPacket;
import me.julionxn.jueguitos.games.freezers.networking.FreezersServerPackets;
import me.julionxn.jueguitos.games.freezers.util.Freeze;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class C2S_SyncFreezeStatePacket implements C2SPacket {

    private static final Identifier ID = new Identifier(Jueguitos.ID, "sync_freeze_state");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void handlePacket(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf) {
        server.execute(() -> {
            boolean state = ((Freeze) player).eufonia$isFreezed();
            PacketByteBuf buf2 = PacketByteBufs.create();
            buf2.writeBoolean(state);
            ServerPlayNetworking.send(player, FreezersServerPackets.S2C_SET_FREEZE_STATE.getIdentifier(), buf2);
        });
    }

}
