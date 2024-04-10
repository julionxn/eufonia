package me.julionxn.jueguitos.core.managers;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.networking.ServerPackets;
import me.julionxn.jueguitos.core.networking.packets.S2C_GameStateEventPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameStateManager {

    private final HashMap<String, Minigame> registeredGames = new HashMap<>();
    private Minigame activeMinigame;
    private final Set<PlayerEntity> players = new HashSet<>();

    public void registerGame(Minigame minigame){
        registeredGames.put(minigame.getId(), minigame);
    }

    public HashMap<String, Minigame> getRegisteredGames(){
        return registeredGames;
    }

    public boolean setActiveMinigame(String id){
        if (!registeredGames.containsKey(id)) return false;
        Minigame minigame = registeredGames.get(id);
        return setActiveMinigame(minigame);
    }

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

    private void updateClientsActiveGame(){
        PacketByteBuf buf = S2C_GameStateEventPacket.buf(activeMinigame, S2C_GameStateEventPacket.CHANGE_GAME_EVENT);
        for (PlayerEntity player : players) {
            ServerPlayNetworking.send((ServerPlayerEntity) player,
                    ServerPackets.S2C_GAME_STATE_EVENT.getIdentifier(),
                    buf);
        }
    }

    @Nullable
    public Minigame getActiveMinigame(){
        return activeMinigame;
    }

    public boolean startGame(@Nullable String args){
        if (activeMinigame == null) return false;
        return activeMinigame.start(players, parseArgs(args));
    }

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

    public boolean stopGame(){
        if (activeMinigame == null) return false;
        return activeMinigame.stop();
    }

    public boolean resetGame(){
        if (activeMinigame == null) return false;
        activeMinigame.reset();
        return true;
    }

    public void joinPlayer(PlayerEntity player){
        players.add(player);
    }

    public void leavePlayer(PlayerEntity player){
        players.remove(player);
    }

    public void clearPlayers(){
        players.clear();
    }

    public Set<PlayerEntity> getPlayers(){
        return players;
    }

    private static class SingletonHolder {
        private static final GameStateManager INSTANCE = new GameStateManager();
    }

    public static GameStateManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

}
