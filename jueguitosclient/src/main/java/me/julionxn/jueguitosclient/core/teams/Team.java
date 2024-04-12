package me.julionxn.jueguitosclient.core.teams;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Representa un equipo para uso dentro de los minijuegos.
 */
public class Team {

    private final String id;
    private final TeamColor teamColor;
    private final Set<UUID> uuids = new HashSet<>();
    private final List<PlayerListEntry> entries = new ArrayList<>();

    public Team(String id, TeamColor teamColor){
        this.id = id;
        this.teamColor = teamColor;
    }

    /**
     * @return El id del equipo.
     */
    public String id(){
        return id;
    }

    /**
     * @return El color del equipo.
     */
    public TeamColor teamColor(){
        return teamColor;
    }

    /**
     * @param uuid El UUID del jugador a agregar al equipo.
     */
    public void addPlayer(UUID uuid){
        uuids.add(uuid);
    }

    /**
     * @param uuid El UUID del jugador a remover del equipo.
     */
    public void removePlayer(UUID uuid){
        uuids.remove(uuid);
    }

    /**
     * @return La colección de las UUID de los jugadores dentro de este equipo.
     */
    public Set<UUID> getUuids(){
        return uuids;
    }

    /**
     * Encargado de obtener las PlayerListEntry's a partir de los UUIDS.
     * @param client La instancia del client.
     */
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

    /**
     * @return Las entries que han sido cargador previamente.
     * @see Team#setupPlayers(MinecraftClient)
     */
    public List<PlayerListEntry> getEntries(){
        return entries;
    }

}
