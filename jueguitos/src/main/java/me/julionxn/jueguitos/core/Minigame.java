package me.julionxn.jueguitos.core;

import me.julionxn.jueguitos.core.teams.TeamsInfo;
import me.julionxn.jueguitos.core.teams.TeamsSetup;
import me.julionxn.jueguitos.core.timer.BasicTimer;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Set;

public interface Minigame {

    String getId();
    TeamsSetup teamsSetup(TeamsSetup teamsSetup);
    default void onModInitialization(){}
    boolean start(Set<PlayerEntity> players, @Nullable HashMap<String, String> args);
    boolean stop();
    void reset();
    default void tick(){}
    boolean isRunning();
    boolean isPlaying();
    @Nullable BasicTimer getTimer();
    @Nullable TeamsInfo getTeamsInfo();
    Set<PlayerEntity> getPlayers();
    void onPlayerHitAnother(PlayerEntity source, PlayerEntity target);

}
