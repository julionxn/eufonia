package me.julionxn.jueguitosclient.core.networking.packets;

import me.julionxn.jueguitosclient.JueguitosClient;
import me.julionxn.jueguitosclient.core.networking.S2CPacket;
import me.julionxn.jueguitosclient.core.screen.InfoScreen;
import me.julionxn.jueguitosclient.core.teams.Team;
import me.julionxn.jueguitosclient.core.teams.TeamColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Paquete recibido desde el servidor para solicitar abrir la ventana de info.
 */
public class S2C_OpenInfoScreenPacket implements S2CPacket {

    private static final Identifier ID = new Identifier(JueguitosClient.ID, "open_info_screen");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void handlePacket(MinecraftClient client, PacketByteBuf buf) {
        boolean shouldHandle = buf.readBoolean();
        if (!shouldHandle) return;
        String gameId = buf.readString();
        Set<Team> teams = new HashSet<>();
        int teamsAmount = buf.readInt();
        for (int i = 0; i < teamsAmount; i++) {
            String teamId = buf.readString();
            TeamColor teamColor = buf.readEnumConstant(TeamColor.class);
            Team team = new Team(teamId, teamColor);
            int playersAmount = buf.readInt();
            for (int i1 = 0; i1 < playersAmount; i1++) {
                UUID uuid = buf.readUuid();
                team.addPlayer(uuid);
            }
            teams.add(team);
        }
        client.execute(() -> {
            client.setScreen(new InfoScreen(gameId, teams));
        });
    }

}
