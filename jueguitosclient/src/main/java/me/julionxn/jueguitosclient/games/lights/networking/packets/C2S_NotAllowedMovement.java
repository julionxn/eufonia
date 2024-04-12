package me.julionxn.jueguitosclient.games.lights.networking.packets;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.networking.C2SPacket;
import me.julionxn.jueguitosclient.games.lights.util.Allowed;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class C2S_NotAllowedMovement implements C2SPacket {

    private static final Identifier ID = new Identifier(JueguitosClient.ID, "not_allowed_movement");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    public static void send(){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        PlayerEntity player = client.player;
        if (player == null) return;
        ((Allowed) player).eufonia$shouldHandle(false);
        ClientPlayNetworking.send(ID, PacketByteBufs.empty());
    }

}
