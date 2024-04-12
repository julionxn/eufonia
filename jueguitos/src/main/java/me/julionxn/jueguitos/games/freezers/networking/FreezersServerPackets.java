package me.julionxn.jueguitos.games.freezers.networking;

import me.julionxn.jueguitos.core.networking.C2SPacket;
import me.julionxn.jueguitos.core.networking.S2CPacket;
import me.julionxn.jueguitos.core.networking.packets.S2C_GameStateEventPacket;
import me.julionxn.jueguitos.games.freezers.networking.packets.S2C_SetFreezeStatePacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class FreezersServerPackets {

    //server -> client
    public static final S2CPacket S2C_SET_FREEZE_STATE = new S2C_SetFreezeStatePacket();

    //client -> server
    public static void register(){

    }

    private static void C2S(C2SPacket packet) {
        ServerPlayNetworking.registerGlobalReceiver(packet.getIdentifier(),
                (server, player, handler, buf, sender) -> packet.handlePacket(server, player, buf));
    }


}