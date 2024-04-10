package me.julionxn.jueguitosclient.core.networking;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.networking.packets.C2S_ChangeTeamPacket;
import me.julionxn.jueguitosclient.core.networking.packets.C2S_KillPlayerPacket;
import me.julionxn.jueguitosclient.core.networking.packets.S2C_GameStateEventPacket;
import me.julionxn.jueguitosclient.core.networking.packets.S2C_OpenInfoScreenPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ClientPackets {

    //server -> client
    public static final S2CPacket S2C_GAME_STATE_EVENT = new S2C_GameStateEventPacket();
    public static final S2CPacket S2C_OPEN_INFO_SCREEN = new S2C_OpenInfoScreenPacket();

    //client -> server
    public static final C2SPacket C2S_CHANGE_TEAM = new C2S_ChangeTeamPacket();
    public static final C2SPacket C2S_KILL_PLAYER = new C2S_KillPlayerPacket();

    //server -> client
    public static void register(){
        s2c(S2C_GAME_STATE_EVENT);
        s2c(S2C_OPEN_INFO_SCREEN);
    }

    private static void s2c(S2CPacket packet) {
        ClientPlayNetworking.registerGlobalReceiver(packet.getIdentifier(),
                (client, handler, buf, sender) -> packet.handlePacket(client, buf));
    }

    private static C2SPacket basicC2S(String packetName) {
        return () -> new Identifier(JueguitosClient.ID, packetName);
    }

}
