package me.julionxn.jueguitos.core.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public interface C2SPacket {

    Identifier getIdentifier();
    void handlePacket(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf);

}
