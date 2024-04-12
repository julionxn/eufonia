package me.julionxn.jueguitosclient.games;

import me.julionxn.jueguitosclient.core.ClientMinigame;
import me.julionxn.jueguitosclient.core.managers.ClientGameStateManager;
import me.julionxn.jueguitosclient.games.freezers.FreezersClientGame;

public class AllClientGames {

    public static void register(){
        register(new HotPotatoClientGame());
        register(new TailsClientGame());
        register(new DuckGooseClientMinigame());
        register(new FreezersClientGame());
    }

    private static void register(ClientMinigame minigame){
        ClientGameStateManager.getInstance().registerGame(minigame);
    }

}
