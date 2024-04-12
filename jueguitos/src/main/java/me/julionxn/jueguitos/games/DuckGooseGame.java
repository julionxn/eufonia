package me.julionxn.jueguitos.games;

import me.julionxn.jueguitos.core.SimpleMinigame;
import me.julionxn.jueguitos.core.teams.Team;
import me.julionxn.jueguitos.core.teams.TeamColor;
import me.julionxn.jueguitos.core.teams.TeamsSetup;
import me.julionxn.jueguitos.core.teams.distribution.Distribution;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class DuckGooseGame extends SimpleMinigame {

    private final String DUCKS_TEAM = "ducks";
    private final String GOOSE_TEAM = "goose";
    private final String HUNTER_TEAM = "hunter";

    @Override
    public String getId() {
        return "duckgoose";
    }

    @Override
    public @NotNull TeamsSetup teamsSetup(TeamsSetup teamsSetup) {
        return teamsSetup.addTeam(new Team(DUCKS_TEAM, TeamColor.YELLOW, Distribution.remaining()))
                .addTeam(new Team(GOOSE_TEAM, TeamColor.BLUE, Distribution.fixed(1)))
                .addTeam(new Team(HUNTER_TEAM, TeamColor.RED, Distribution.fixed(1)));
    }

    @Override
    public void onPlayerHitAnother(PlayerEntity source, PlayerEntity target) {
        if (teamsInfo == null) return;
        getTeamOfPlayers(source, target).ifPresent(teams -> {
            Team sourceTeam = teams.sourceTeam();
            Team targetTeam = teams.targetTeam();
            if (sourceTeam.id().equals(HUNTER_TEAM) && targetTeam.id().equals(GOOSE_TEAM)){
                addGlowing(source);
                addGlowing(target);
                players.forEach(player -> player.sendMessage(Text.of("Se ha encontrado al ganzo " + target.getName())));
            }
        });
    }

    @Override
    protected @Nullable Integer secondsOfGame() {
        return 120;
    }

    @Override
    protected void onStart() {
        if (teamsInfo == null) return;
        teamsInfo.getPlayersInTeam(players, GOOSE_TEAM).forEach(player -> player.sendMessage(Text.of("Eres el ganzo. Escondete del cazador.")));
        teamsInfo.getPlayersInTeam(players, HUNTER_TEAM).forEach(player -> player.sendMessage(Text.of("Eres el cazador. Encuentra al ganzo.")));
        teamsInfo.getPlayersInTeam(players, DUCKS_TEAM).forEach(player -> player.sendMessage(Text.of("Eres un pato.")));
    }

    @Override
    protected void onSetup(@Nullable HashMap<String, String> args) {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onReset() {

    }

    @Override
    public void tick() {
        if (timer == null) return;
        if (timer.isDone()){
            players.forEach(player -> player.sendMessage(Text.of("Juego terminado.")));
            reset();
        }
    }

}
