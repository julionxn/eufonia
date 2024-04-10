package me.julionxn.jueguitosclient.core.teams;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Team {

    private final String id;
    private final TeamColor teamColor;
    private final Set<UUID> uuids = new HashSet<>();
    private final List<PlayerListEntry> entries = new ArrayList<>();

    public Team(String id, TeamColor teamColor){
        this.id = id;
        this.teamColor = teamColor;
    }

    public String id(){
        return id;
    }

    public TeamColor teamColor(){
        return teamColor;
    }

    public void addPlayer(UUID uuid){
        uuids.add(uuid);
    }

    public void removePlayer(UUID uuid){
        uuids.remove(uuid);
    }

    public Set<UUID> getUuids(){
        return uuids;
    }

    public void setupPlayers(@NotNull MinecraftClient client){
        ClientPlayerEntity player = client.player;
        if (player == null) return;
        entries.clear();
        ClientPlayNetworkHandler networkHandler = player.networkHandler;
        for (UUID uuid : uuids) {
            PlayerListEntry entry = networkHandler.getPlayerListEntry(uuid);
            entries.add(entry);
        }
    }

    public List<PlayerListEntry> getEntries(){
        return entries;
    }

}
