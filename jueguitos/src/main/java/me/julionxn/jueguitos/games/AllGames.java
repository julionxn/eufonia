package me.julionxn.jueguitos.games;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import me.julionxn.jueguitos.games.freezers.FreezersGame;
import me.julionxn.jueguitos.games.lights.LightsGame;

public class AllGames {

    public static final FreezersGame FREEZERS_GAME = new FreezersGame();

    public static void register(){
        register(new HotPotatoGame());
        register(new TailsGame());
        register(new DuckGooseGame());
        register(FREEZERS_GAME);
        register(new LightsGame());
    }

    private static void register(Minigame minigame){
        GameStateManager.getInstance().registerGame(minigame);
    }

}
