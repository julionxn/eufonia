package me.julionxn.jueguitos.core.networking;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.networking.packets.C2S_ChangeTeamPacket;
import me.julionxn.jueguitos.core.networking.packets.C2S_KillPlayerPacket;
import me.julionxn.jueguitos.core.networking.packets.S2C_GameStateEventPacket;
import me.julionxn.jueguitos.core.networking.packets.S2C_OpenInfoScreenPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ServerPackets {

    //server -> client
    public static final S2CPacket S2C_GAME_STATE_EVENT = new S2C_GameStateEventPacket();
    public static final S2CPacket S2C_OPEN_INFO_SCREEN = new S2C_OpenInfoScreenPacket();

    //client -> server
    public static final C2SPacket C2S_CHANGE_TEAM = new C2S_ChangeTeamPacket();
    public static final C2SPacket C2S_KILL_PLAYER = new C2S_KillPlayerPacket();

    //client -> server
    public static void register(){
        C2S(C2S_CHANGE_TEAM);
        C2S(C2S_KILL_PLAYER);
    }

    private static void C2S(C2SPacket packet) {
        ServerPlayNetworking.registerGlobalReceiver(packet.getIdentifier(),
                (server, player, handler, buf, sender) -> packet.handlePacket(server, player, buf));
    }

    private static S2CPacket basicC2S(String packetName) {
        return () -> new Identifier(Jueguitos.ID, packetName);
    }

}
