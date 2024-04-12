package me.julionxn.jueguitosclient.core.networking.packets;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.networking.C2SPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

/**
 * Paquete usado para que el servidor mate a un jugador.
 */
public class C2S_KillPlayerPacket implements C2SPacket {

    private static final Identifier ID = new Identifier(JueguitosClient.ID, "kill_player");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    /**
     * @param uuid El UUID del jugador a matar.
     * @return El respectivo paquete.
     */
    public static PacketByteBuf buf(UUID uuid){
        return PacketByteBufs.create().writeUuid(uuid);
    }

}
