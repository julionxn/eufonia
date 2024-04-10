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
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class HotPotatoGame extends SimpleMinigame {

    private int startingPotatoes = 1;
    private static final String POTATO_TEAM = "potato";
    private static final String CLEAN_TEAM = "clean";

    @Override
    public String getId() {
        return "hotpotato";
    }

    @Override
    protected @Nullable Integer secondsOfGame() {
        return 120;
    }

    @Override
    protected void onSetup(@Nullable HashMap<String, String> args) {
        if (args == null) return;
        String startingPotatoes = args.get("startingPotatoes");
        if (startingPotatoes == null) return;
        this.startingPotatoes = Integer.parseInt(startingPotatoes);
    }

    @Override
    public TeamsSetup teamsSetup(TeamsSetup teamsSetup) {
        return teamsSetup.addTeam(new Team(POTATO_TEAM, TeamColor.RED, Distribution.fixed(startingPotatoes)))
                .addTeam(new Team(CLEAN_TEAM, TeamColor.BLUE, Distribution.remaining()));
    }

    @Override
    protected void onStart() {
        if (teamsInfo == null) return;
        teamsInfo.getPlayersInTeam(players, POTATO_TEAM).forEach(this::potatoed);
        teamsInfo.getPlayersInTeam(players, CLEAN_TEAM).forEach(player ->
                player.sendMessage(Text.of("No tienes la patata."), true)
        );
    }

    @Override
    protected void onStop() {

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
    protected void onReset() {
        startingPotatoes = 1;
        if (teamsInfo == null) return;
        teamsInfo.getPlayersInTeam(players, POTATO_TEAM).forEach(this::despotatoed);
    }

    @Override
    public void onPlayerHitAnother(PlayerEntity source, PlayerEntity target) {
        if (teamsInfo == null) return;
        getTeamOfPlayers(source, target).ifPresent(teams -> {
            Team targetTeam = teams.targetTeam();
            Team sourceTeam = teams.sourceTeam();
            if (sourceTeam.id().equals(POTATO_TEAM) && targetTeam.id().equals(CLEAN_TEAM)){
                despotatoed(source);
                teamsInfo.addPlayerToTeam(source, targetTeam);
                potatoed(target);
                teamsInfo.addPlayerToTeam(target, sourceTeam);
            }
        });
    }

    private void potatoed(PlayerEntity player){
        addGlowing(player);
        player.giveItemStack(new ItemStack(Items.BAKED_POTATO, 1));
        player.sendMessage(Text.of("Tienes la patata."), true);
    }

    private void despotatoed(PlayerEntity player){
        removeGlowing(player);
        player.getInventory().clear();
        player.sendMessage(Text.of("Ya no tienes la patata."), true);
    }

}
