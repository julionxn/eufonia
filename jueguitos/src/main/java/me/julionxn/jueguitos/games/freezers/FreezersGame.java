package me.julionxn.jueguitos.games.freezers;

import me.julionxn.jueguitos.core.SimpleMinigame;
import me.julionxn.jueguitos.core.teams.Team;
import me.julionxn.jueguitos.core.teams.TeamColor;
import me.julionxn.jueguitos.core.teams.TeamsSetup;
import me.julionxn.jueguitos.core.teams.distribution.Distribution;
import me.julionxn.jueguitos.games.freezers.networking.packets.S2C_SetFreezeStatePacket;
import me.julionxn.jueguitos.games.freezers.util.Freeze;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class FreezersGame extends SimpleMinigame {

    private final ZonesConfig config = new ZonesConfig();

    @Override
    public String getId() {
        return "freezers";
    }

    @Override
    protected @Nullable Integer secondsOfGame() {
        return 120;
    }

    @Override
    public void onModInitialization() {
        config.load();
    }

    @Override
    protected void onSetup(@Nullable HashMap<String, String> args) {

    }

    @Override
    public @NotNull TeamsSetup teamsSetup(TeamsSetup teamsSetup) {
        return teamsSetup.addTeam(new Team("red", TeamColor.RED, Distribution.remaining()))
                .addTeam(new Team("blue", TeamColor.BLUE, Distribution.remaining()));
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
        getTeamOfPlayers(source, target).ifPresent(playerTeams -> {
            HashMap<String, Zone> zones = config.getZones();
            String sourceTeam = playerTeams.sourceTeam().id();
            String targetTeam = playerTeams.targetTeam().id();
            Zone sourceZone = zones.get(sourceTeam);
            Zone targetZone = zones.get(targetTeam);
            if (sourceZone == null || targetZone == null) return;
            Freeze freezeTarget = (Freeze) target;
            if (!sourceTeam.equals(targetTeam)){
                if (sourceZone.isInside(target)){
                    S2C_SetFreezeStatePacket.setFreezeState(target, true);
                }
            } else if (freezeTarget.eufonia$isFreezed()) {
                S2C_SetFreezeStatePacket.setFreezeState(target, false);
            }
        });
    }

    public void reloadConfig(){
        config.load();
    }

}
