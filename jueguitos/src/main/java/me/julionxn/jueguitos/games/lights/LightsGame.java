package me.julionxn.jueguitos.games.lights;

import me.julionxn.jueguitos.core.SimpleMinigame;
import me.julionxn.jueguitos.core.teams.Team;
import me.julionxn.jueguitos.core.teams.TeamColor;
import me.julionxn.jueguitos.core.teams.TeamsSetup;
import me.julionxn.jueguitos.core.teams.distribution.Distribution;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class LightsGame extends SimpleMinigame {

    @Override
    public String getId() {
        return "lights";
    }

    @Override
    protected @Nullable Integer secondsOfGame() {
        return 120;
    }

    @Override
    protected void onSetup(@Nullable HashMap<String, String> args) {

    }

    @Override
    public @NotNull TeamsSetup teamsSetup(TeamsSetup teamsSetup) {
        return teamsSetup.addTeam(new Team("winners", TeamColor.BLUE, Distribution.remaining()))
                .addTeam(new Team("losers", TeamColor.RED, Distribution.fixed(0)));
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onReset() {

    }

    public void markAsMoved(PlayerEntity player){
        if (teamsInfo == null) return;
        teamsInfo.getTeam("losers").ifPresent(team -> teamsInfo.addPlayerToTeam(player, team));
    }

    @Override
    public void onPlayerHitAnother(PlayerEntity source, PlayerEntity target) {

    }

}
