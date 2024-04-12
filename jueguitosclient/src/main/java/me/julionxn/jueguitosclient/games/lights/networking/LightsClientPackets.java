package me.julionxn.jueguitosclient.games.lights.networking;

import me.julionxn.jueguitosclient.core.networking.C2SPacket;
import me.julionxn.jueguitosclient.core.networking.S2CPacket;
import me.julionxn.jueguitosclient.games.lights.networking.packets.C2S_NotAllowedMovement;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class LightsClientPackets {

    //client -> server
    public static final C2SPacket C2S_NOT_ALLOWED_MOVEMENT = new C2S_NotAllowedMovement();

    //server -> client
    public static void register(){

    }

    private static void s2c(S2CPacket packet) {
        ClientPlayNetworking.registerGlobalReceiver(packet.getIdentifier(),
                (client, handler, buf, sender) -> packet.handlePacket(client, buf));
    }

}
