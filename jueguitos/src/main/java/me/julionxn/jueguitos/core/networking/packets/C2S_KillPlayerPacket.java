package me.julionxn.jueguitos.core.networking.packets;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.networking.C2SPacket;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class C2S_KillPlayerPacket implements C2SPacket {

    private static final Identifier ID = new Identifier(Jueguitos.ID, "kill_player");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void handlePacket(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf) {
        UUID uuid = buf.readUuid();
        server.execute(() -> {
            ServerPlayerEntity playerToKill = server.getPlayerManager().getPlayer(uuid);
            if (playerToKill == null) return;
            playerToKill.kill();
        });
    }

}
