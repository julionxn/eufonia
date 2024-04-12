package me.julionxn.jueguitosclient.games.freezers;

import me.julionxn.jueguitosclient.core.BasicClientMinigame;
import me.julionxn.jueguitosclient.games.freezers.networking.FreezersClientPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class FreezersClientGame extends BasicClientMinigame {

    @Override
    public String getId() {
        return "freezers";
    }

    @Override
    protected void onStart(PacketByteBuf buf) {

    }

    @Override
    protected void onStop(PacketByteBuf buf) {

    }

    @Override
    protected void onReset(PacketByteBuf buf) {

    }

    public static void onJoin(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        if (client.player != null){
            ClientPlayNetworking.send(FreezersClientPackets.S2C_SET_FREEZE_STATE.getIdentifier(), PacketByteBufs.empty());
        }
    }

}
