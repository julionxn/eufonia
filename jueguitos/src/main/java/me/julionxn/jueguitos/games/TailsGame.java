package me.julionxn.jueguitos.games;

import me.julionxn.jueguitos.core.SimpleMinigame;
import me.julionxn.jueguitos.core.teams.Team;
import me.julionxn.jueguitos.core.teams.TeamColor;
import me.julionxn.jueguitos.core.teams.TeamsSetup;
import me.julionxn.jueguitos.core.teams.distribution.Distribution;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class TailsGame extends SimpleMinigame {

    private final String TAILS_TEAM = "tail";
    private final String CLEAR_TEAM = "clear";

    @Override
    public String getId() {
        return "tails";
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
        return teamsSetup.addTeam(new Team(TAILS_TEAM, TeamColor.YELLOW, Distribution.remaining()))
                .addTeam(new Team(CLEAR_TEAM, TeamColor.RED, Distribution.remaining()));
    }

    @Override
    protected void onStart() {
        if (teamsInfo == null) return;
        teamsInfo.getPlayersInTeam(players, TAILS_TEAM).forEach(this::tailed);
        teamsInfo.getPlayersInTeam(players, CLEAR_TEAM).forEach(player ->
                player.sendMessage(Text.of("No tienes la cola."), true)
        );
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onReset() {
        if (teamsInfo == null) return;
        teamsInfo.getPlayersInTeam(players, TAILS_TEAM).forEach(this::destailed);
    }

    @Override
    public void tick() {
        if (timer == null) return;
        if (timer.isDone()){
            players.forEach(player -> player.sendMessage(Text.of("Juego terminado.")));
            reset();
        }
    }

    @Override
    public void onPlayerHitAnother(PlayerEntity source, PlayerEntity target) {
        if (teamsInfo == null) return;
        getTeamOfPlayers(source, target).ifPresent(teams -> {
            Team targetTeam = teams.targetTeam();
            Team sourceTeam = teams.sourceTeam();
            if (targetTeam.id().equals(TAILS_TEAM) && !sourceTeam.id().equals(TAILS_TEAM)){
                tailed(source);
                teamsInfo.addPlayerToTeam(source, targetTeam);
                destailed(target);
                teamsInfo.addPlayerToTeam(target, sourceTeam);
            }
        });
    }

    private void tailed(PlayerEntity player){
        addGlowing(player);
        player.giveItemStack(new ItemStack(Items.RABBIT_FOOT, 1));
        player.sendMessage(Text.of("Tienes la cola."), true);
    }

    private void destailed(PlayerEntity player){
        removeGlowing(player);
        player.getInventory().clear();
        player.sendMessage(Text.of("Ya no tienes la cola."), true);
    }

}
