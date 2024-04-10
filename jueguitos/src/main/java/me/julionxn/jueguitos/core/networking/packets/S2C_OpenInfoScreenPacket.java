package me.julionxn.jueguitos.core.networking.packets;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.networking.S2CPacket;
import me.julionxn.jueguitos.core.teams.Team;
import me.julionxn.jueguitos.core.teams.TeamsInfo;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class S2C_OpenInfoScreenPacket implements S2CPacket {

    private static final Identifier ID = new Identifier(Jueguitos.ID, "open_info_screen");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    public static PacketByteBuf buf(Minigame minigame){
        PacketByteBuf buf = PacketByteBufs.create();
        TeamsInfo info = minigame.getTeamsInfo();
        if (info == null) {
            buf.writeBoolean(false);
            return buf;
        }
        buf.writeBoolean(true);
        buf.writeString(minigame.getId());
        Set<Team> teams = info.getTeams();
        HashMap<UUID, Team> players = info.getPlayers();
        buf.writeInt(teams.size());
        Set<Map.Entry<UUID, Team>> entries = players.entrySet();
        for (Team team : teams) {
            buf.writeString(team.id());
            buf.writeEnumConstant(team.teamColor());
            Set<Map.Entry<UUID, Team>> filtered = entries.stream()
                    .filter(entry -> entry.getValue().id().equals(team.id()))
                    .collect(Collectors.toSet());
            buf.writeInt(filtered.size());
            for (Map.Entry<UUID, Team> entry : filtered) {
                buf.writeUuid(entry.getKey());
            }
        }
        return buf;
    }

}
