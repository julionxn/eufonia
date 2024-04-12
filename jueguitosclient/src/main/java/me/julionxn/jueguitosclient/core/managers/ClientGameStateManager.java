package me.julionxn.jueguitosclient.core.managers;

import me.julionxn.jueguitosclient.core.ClientMinigame;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Clase encargada de manejar y contener al juego que se encuentra activo en la parte del cliente.
 */
public class ClientGameStateManager {

    private final HashMap<String, ClientMinigame> registeredGames = new HashMap<>();
    private ClientMinigame activeMinigame;

    /**
     * Registrar un minijuego del lado del cliente.
     * @param minigame La instancia de un ClientMinigame
     */
    public void registerGame(ClientMinigame minigame){
        registeredGames.put(minigame.getId(), minigame);
    }

    /**
     * Establecer el minijuego actual.
     * @param id Id del minijuego.
     */
    public void setActiveMinigame(String id){
        if (!registeredGames.containsKey(id)) return;
        ClientMinigame minigame = registeredGames.get(id);
        setActiveMinigame(minigame);
    }

    /**
     * Establecer el minijuego actual.
     * @param minigame La instancia del minijuego.
     */
    public void setActiveMinigame(ClientMinigame minigame){
        if (activeMinigame == null) activeMinigame = minigame;
        if (activeMinigame.isRunning() || !registeredGames.containsValue(minigame)){
            return;
        }
        activeMinigame = minigame;
    }

    /**
     * @return La instancia del minijuego activo.
     */
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
