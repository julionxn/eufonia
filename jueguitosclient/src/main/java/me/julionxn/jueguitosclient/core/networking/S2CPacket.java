package me.julionxn.jueguitosclient.core.networking;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface S2CPacket {

    Identifier getIdentifier();
    void handlePacket(MinecraftClient client, PacketByteBuf buf);

}
