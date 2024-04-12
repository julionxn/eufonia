package me.julionxn.jueguitos.games.lights.networking;

import me.julionxn.jueguitos.core.networking.C2SPacket;
import me.julionxn.jueguitos.games.lights.networking.packets.C2S_NotAllowedMovementPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class LightsServerPackets {

    //client -> server
    public static final C2SPacket C2S_NOT_ALLOWED_MOVEMENT = new C2S_NotAllowedMovementPacket();

    //client -> server
    public static void register(){
        C2S(C2S_NOT_ALLOWED_MOVEMENT);
    }

    private static void C2S(C2SPacket packet) {
        ServerPlayNetworking.registerGlobalReceiver(packet.getIdentifier(),
                (server, player, handler, buf, sender) -> packet.handlePacket(server, player, buf));
    }

}
