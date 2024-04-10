package me.julionxn.jueguitos.core;

import me.julionxn.jueguitos.core.networking.ServerPackets;
import me.julionxn.jueguitos.core.networking.packets.S2C_GameStateEventPacket;
import me.julionxn.jueguitos.core.teams.TeamsInfo;
import me.julionxn.jueguitos.core.teams.TeamsSetup;
import me.julionxn.jueguitos.core.timer.BasicTimer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class SimpleMinigame implements Minigame {

    private boolean playing = false;
    private boolean cleared = true;
    @Nullable
    protected BasicTimer timer;
    @Nullable
    protected TeamsInfo teamsInfo;
    protected Set<PlayerEntity> players;

    protected abstract @Nullable Integer secondsOfGame();

    public void start(Set<PlayerEntity> players) {
        start(players, null);
    }

    @Override
    public boolean start(Set<PlayerEntity> players, @Nullable HashMap<String, String> args) {
        if (playing) return false;
        playing = true;
        if (cleared) {
            setup(players, args);
            cleared = false;
        }
        if (timer != null) timer.run();
        onStart();
        PacketByteBuf buf = S2C_GameStateEventPacket.buf(this, S2C_GameStateEventPacket.START_EVENT);
        PacketByteBuf finalBuf = startGameStateEventBuf(buf);
        players.forEach(player -> sendGameStateEventPacket(player, finalBuf));
        return true;
    }

    protected PacketByteBuf startGameStateEventBuf(PacketByteBuf buf){
        return buf;
    }

    protected abstract void onStart();

    private void setup(Set<PlayerEntity> players, @Nullable HashMap<String, String> args){
        TeamsSetup teamsSetup = teamsSetup(new TeamsSetup());
        teamsInfo = new TeamsInfo(teamsSetup);
        setupTeams(players);
        Integer time = secondsOfGame();
        if (time != null){
            timer = new BasicTimer(time);
        }
        onSetup(args);
    }

    private void setupTeams(Set<PlayerEntity> players){
        if (teamsInfo == null) return;
        List<PlayerEntity> playersList = new ArrayList<>(players);
        teamsInfo.setupPlayers(playersList);
        this.players = players;
    }

    protected abstract void onSetup(@Nullable HashMap<String, String> args);

    @Override
    public boolean stop() {
        if (!playing) return false;
        playing = false;
        if (timer != null) timer.stop();
        onStop();
        PacketByteBuf buf = S2C_GameStateEventPacket.buf(this, S2C_GameStateEventPacket.STOP_EVENT);
        PacketByteBuf finalBuf = stopGameStateEventBuf(buf);
        players.forEach(player -> sendGameStateEventPacket(player, finalBuf));
        return true;
    }

    protected PacketByteBuf stopGameStateEventBuf(PacketByteBuf buf){
        return buf;
    }

    protected abstract void onStop();

    @Override
    public void reset() {
        cleared = true;
        stop();
        onReset();
        if (teamsInfo != null){
            teamsInfo.resetTeams();
        }
        PacketByteBuf buf = S2C_GameStateEventPacket.buf(this, S2C_GameStateEventPacket.RESET_EVENT);
        PacketByteBuf finalBuf = resetGameStateEventBuf(buf);
        players.forEach(player -> sendGameStateEventPacket(player, finalBuf));
    }

    protected PacketByteBuf resetGameStateEventBuf(PacketByteBuf buf){
        return buf;
    }

    protected abstract void onReset();

    private void sendGameStateEventPacket(PlayerEntity player, PacketByteBuf buf){
        ServerPlayNetworking.send((ServerPlayerEntity) player,
                        ServerPackets.S2C_GAME_STATE_EVENT.getIdentifier(), buf);
    }

    public boolean isRunning() {
        return playing || !cleared;
    }

    public boolean isPlaying(){
        return playing;
    }

    @Nullable
    @Override
    public BasicTimer getTimer() {
        return timer;
    }

    @Nullable
    @Override
    public TeamsInfo getTeamsInfo() {
        return teamsInfo;
    }

    @Override
    public Set<PlayerEntity> getPlayers() {
        return players;
    }

    protected void addGlowing(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 0x989680, 0, false, false));
    }

    protected void removeGlowing(PlayerEntity player){
        player.removeStatusEffect(StatusEffects.GLOWING);
    }

}
