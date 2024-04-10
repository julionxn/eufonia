package me.julionxn.jueguitos.core.networking.packets;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import me.julionxn.jueguitos.core.networking.C2SPacket;
import me.julionxn.jueguitos.core.teams.TeamsInfo;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class C2S_ChangeTeamPacket implements C2SPacket {

    private static final Identifier ID = new Identifier(Jueguitos.ID, "change_team");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void handlePacket(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf) {
        String newTeam = buf.readString();
        UUID uuid = buf.readUuid();
        server.execute(() -> {
            Minigame minigame = GameStateManager.getInstance().getActiveMinigame();
            if (minigame == null) return;
            ServerPlayerEntity handledPlayer = server.getPlayerManager().getPlayer(uuid);
            if (handledPlayer == null) return;
            TeamsInfo teamsInfo = minigame.getTeamsInfo();
            if (teamsInfo == null) return;
            teamsInfo.getTeam(newTeam).ifPresent(team -> {
                teamsInfo.removeTeamFromPlayer(player);
                teamsInfo.addPlayerToTeam(player, team);
            });
        });
    }

}
