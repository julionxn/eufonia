package me.julionxn.jueguitosclient.games;

import me.julionxn.jueguitosclient.core.ClientMinigame;
import me.julionxn.jueguitosclient.core.managers.ClientGameStateManager;

public class AllClientGames {

    public static void register(){
        register(new HotPotatoClientGame());
    }

    private static void register(ClientMinigame minigame){
        ClientGameStateManager.getInstance().registerGame(minigame);
    }

}
