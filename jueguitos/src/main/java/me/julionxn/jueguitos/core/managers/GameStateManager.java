package me.julionxn.jueguitos.core.managers;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.networking.ServerPackets;
import me.julionxn.jueguitos.core.networking.packets.S2C_GameStateEventPacket;
import me.julionxn.jueguitos.core.teams.TeamColor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clase encargada de manejar y contener al juego que se encuentra activo en la parte del servidor.
 * Además, se encarga de contener a todos los jugadores que son parte del sistema de minijuegos.
 */
public class GameStateManager {

    private final HashMap<String, Minigame> registeredGames = new HashMap<>();
    private Minigame activeMinigame;
    private final Set<PlayerEntity> players = new HashSet<>();
    private final HashMap<TeamColor, Team> teamColorTeamHashMap = new HashMap<>();

    /**
     * Registrar un minijuego del lado del servidor.
     * @param minigame La instancia de un Minigame
     */
    public void registerGame(Minigame minigame){
        registeredGames.put(minigame.getId(), minigame);
    }

    /**
     * @return Todos los minijuegos que han sido registrados.
     */
    public HashMap<String, Minigame> getRegisteredGames(){
        return registeredGames;
    }

    public void setupTeams(MinecraftServer server){
        if (!teamColorTeamHashMap.isEmpty()) return;
        ServerScoreboard scoreboard = server.getScoreboard();
        addTeam(scoreboard, "red", Formatting.RED, TeamColor.RED);
        addTeam(scoreboard, "blue", Formatting.BLUE, TeamColor.BLUE);
        addTeam(scoreboard, "yellow", Formatting.YELLOW, TeamColor.YELLOW);
    }

    private void addTeam(ServerScoreboard scoreboard, String id, Formatting color, TeamColor teamColor){
        Team team = scoreboard.addTeam(id);
        team.setColor(color);
        teamColorTeamHashMap.put(teamColor, team);
    }

    public void addToScoreboardTeam(PlayerEntity player, TeamColor teamColor){
        Team team = teamColorTeamHashMap.get(teamColor);
        if (team == null) return;
        System.out.println(player.getName().getString());
        team.getScoreboard().addPlayerToTeam(player.getName().getString(), team);
    }

    public void removeFromScoreboardTeam(PlayerEntity player, TeamColor teamColor){
        Team team = teamColorTeamHashMap.get(teamColor);
        if (team == null) return;
        System.out.println(player.getName().getString());
        team.getScoreboard().removePlayerFromTeam(player.getName().getString(), team);
    }

    /**
     * Establecer el minijuego actual.
     * @param id Id del minijuego.
     * @return Si la operación ha sido exitosa.
     */
    public boolean setActiveMinigame(String id){
        if (!registeredGames.containsKey(id)) return false;
        Minigame minigame = registeredGames.get(id);
        return setActiveMinigame(minigame);
    }

    /**
     * Establecer el minijuego actual.
     * @param minigame La instancia del minijuego.
     * @return Si la operación ha sido exitosa.
     */
    public boolean setActiveMinigame(Minigame minigame){
        if (activeMinigame == null) {
            activeMinigame = minigame;
            updateClientsActiveGame();
            return true;
        }
        if (activeMinigame.isRunning() || !registeredGames.containsValue(minigame)){
            return false;
        }
        activeMinigame = minigame;
        updateClientsActiveGame();
        return true;
    }

    /**
     * Mandar a los jugadores el paquete correspondiente actualizando el minijuego actual.
     */
    private void updateClientsActiveGame(){
        PacketByteBuf buf = S2C_GameStateEventPacket.buf(activeMinigame, S2C_GameStateEventPacket.CHANGE_GAME_EVENT);
        for (PlayerEntity player : players) {
            ServerPlayNetworking.send((ServerPlayerEntity) player,
                    ServerPackets.S2C_GAME_STATE_EVENT.getIdentifier(),
                    buf);
        }
    }

    /**
     * @return El minijuego activo.
     */
    @Nullable
    public Minigame getActiveMinigame(){
        return activeMinigame;
    }

    /**
     * Iniciar el minijuego activo.
     * @param args Argumentos extras, cada argumento separado por un coma (,) de la forma key=value
     * @return Si la operación ha sido exitosa.
     */
    public boolean startGame(@Nullable String args){
        if (activeMinigame == null) return false;
        Set<PlayerEntity> playerEntitySet = players.stream()
                .filter(player -> !((ServerPlayerEntity) player).isDisconnected())
                .collect(Collectors.toSet());
        return activeMinigame.start(playerEntitySet, parseArgs(args));
    }

    /**
     * @param args Argumentos extras.
     * @return Los argumentos parseados key-value
     */
    private HashMap<String, String> parseArgs(@Nullable String args){
        if (args == null) return null;
        if (args.isEmpty()) return null;
        String[] allArgs = args.split(",");
        HashMap<String, String> argsMap = new HashMap<>();
        for (String oneArg : allArgs) {
            String[] parts = oneArg.split("=");
            if (parts.length != 2) continue;
            argsMap.put(parts[0], parts[1]);
        }
        return argsMap;
    }

    /**
     * Detener el minijuego actual.
     * @return Si la operación ha sido exitosa.
     */
    public boolean stopGame(){
        if (activeMinigame == null) return false;
        return activeMinigame.stop();
    }

    /**
     * Resetear minijuego actual.
     * @return Si la operación ha sido exitosa.
     */
    public boolean resetGame(){
        if (activeMinigame == null) return false;
        activeMinigame.reset();
        return true;
    }

    /**
     * Añadir un jugador al sistema.
     * @param player El jugador.
     */
    public void joinPlayer(PlayerEntity player){
        players.add(player);
    }

    /**
     * Remover un jugador del sistema.
     * @param player El jugador.
     */
    public void leavePlayer(PlayerEntity player){
        players.remove(player);
    }

    /**
     * Remover todos los jugadores del sistema.
     */
    public void clearPlayers(){
        players.clear();
    }

    private static class SingletonHolder {
        private static final GameStateManager INSTANCE = new GameStateManager();
    }

    public static GameStateManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

}
