package me.julionxn.jueguitosclient.games.freezers.networking;

import me.julionxn.jueguitosclient.core.networking.S2CPacket;
import me.julionxn.jueguitosclient.games.freezers.networking.packets.S2C_SetFreezeStatePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FreezersClientPackets {

    //server -> client
    public static final S2CPacket S2C_SET_FREEZE_STATE = new S2C_SetFreezeStatePacket();

    public static void register(){
        s2c(S2C_SET_FREEZE_STATE);
    }

    private static void s2c(S2CPacket packet) {
        ClientPlayNetworking.registerGlobalReceiver(packet.getIdentifier(),
                (client, handler, buf, sender) -> packet.handlePacket(client, buf));
    }

}
