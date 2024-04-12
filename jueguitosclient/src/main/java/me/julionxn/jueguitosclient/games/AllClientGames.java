package me.julionxn.jueguitosclient.games;

import me.julionxn.jueguitosclient.core.ClientMinigame;
import me.julionxn.jueguitosclient.core.managers.ClientGameStateManager;
import me.julionxn.jueguitosclient.games.freezers.FreezersClientGame;
import me.julionxn.jueguitosclient.games.lights.LightsClientGame;

public class AllClientGames {

    public static void register(){
        register(new HotPotatoClientGame());
        register(new TailsClientGame());
        register(new DuckGooseClientGame());
        register(new FreezersClientGame());
        register(new LightsClientGame());
    }

    private static void register(ClientMinigame minigame){
        ClientGameStateManager.getInstance().registerGame(minigame);
    }

}
