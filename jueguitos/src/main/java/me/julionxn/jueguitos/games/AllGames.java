package me.julionxn.jueguitos.games;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import me.julionxn.jueguitos.games.freezers.FreezersGame;

public class AllGames {

    public static void register(){
        register(new HotPotatoGame());
        register(new TailsGame());
        register(new DuckGooseGame());
        register(new FreezersGame());
    }

    private static void register(Minigame minigame){
        GameStateManager.getInstance().registerGame(minigame);
    }

}
