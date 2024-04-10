package me.julionxn.jueguitosclient.core.networking.packets;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.networking.C2SPacket;
import me.julionxn.jueguitosclient.core.teams.Team;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class C2S_ChangeTeamPacket implements C2SPacket {

    private static final Identifier ID = new Identifier(JueguitosClient.ID, "change_team");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    public static PacketByteBuf buf(Team newTeam, UUID uuid){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(newTeam.id());
        buf.writeUuid(uuid);
        return buf;
    }

}
