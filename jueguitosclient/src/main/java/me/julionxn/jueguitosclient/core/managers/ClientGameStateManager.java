package me.julionxn.jueguitosclient.core.managers;

import me.julionxn.jueguitosclient.core.ClientMinigame;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ClientGameStateManager {

    private final HashMap<String, ClientMinigame> registeredGames = new HashMap<>();
    private ClientMinigame activeMinigame;

    public void registerGame(ClientMinigame minigame){
        registeredGames.put(minigame.getId(), minigame);
    }

    public void setActiveMinigame(String id){
        if (!registeredGames.containsKey(id)) return;
        ClientMinigame minigame = registeredGames.get(id);
        setActiveMinigame(minigame);
    }

    public void setActiveMinigame(ClientMinigame minigame){
        if (activeMinigame == null) activeMinigame = minigame;
        if (activeMinigame.isRunning() || !registeredGames.containsValue(minigame)){
            return;
        }
        activeMinigame = minigame;
    }

    @Nullable
    public ClientMinigame getActiveMinigame(){
        return activeMinigame;
    }

    private static class SingletonHolder {
        private static final ClientGameStateManager INSTANCE = new ClientGameStateManager();
    }

    public static ClientGameStateManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

}
