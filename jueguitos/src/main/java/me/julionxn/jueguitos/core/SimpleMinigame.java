package me.julionxn.jueguitos.core;

import me.julionxn.jueguitos.core.networking.ServerPackets;
import me.julionxn.jueguitos.core.networking.packets.S2C_GameStateEventPacket;
import me.julionxn.jueguitos.core.teams.Team;
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

import java.util.*;

public abstract class SimpleMinigame implements Minigame {

    private boolean playing = false;
    private boolean cleared = true;
    @Nullable
    protected BasicTimer timer;
    @Nullable
    protected TeamsInfo teamsInfo;
    protected Set<PlayerEntity> players;

    /**
     * @return La cantidad de tiempo en segundos que durará el minijuego.
     * Si se pasa como argumento null, no se creará un contador.
     */
    protected abstract @Nullable Integer secondsOfGame();

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

    /**
     * @param buf El buf que será enviado al cliente al iniciar el juego.
     * @return El mismo buf.
     */
    protected PacketByteBuf startGameStateEventBuf(PacketByteBuf buf){
        return buf;
    }

    /**
     * Ejecutado después de que se empezó el minijuego.
     */
    protected abstract void onStart();

    /**
     * Cargar los equipos y crear el contador.
     * @param players Los jugadores que jugarán.
     * @param args Argumentos extra.
     */
    private void setup(Set<PlayerEntity> players, @Nullable HashMap<String, String> args){
        onSetup(args);
        TeamsSetup teamsSetup = teamsSetup(new TeamsSetup());
        teamsInfo = new TeamsInfo(teamsSetup);
        setupTeams(players);
        Integer time = secondsOfGame();
        if (time != null){
            timer = new BasicTimer(time);
        }
    }

    /**
     * Cargar los equipos.
     * @param players Los jugadores.
     */
    private void setupTeams(Set<PlayerEntity> players){
        if (teamsInfo == null) return;
        List<PlayerEntity> playersList = new ArrayList<>(players);
        teamsInfo.setupPlayers(playersList);
        this.players = players;
    }

    /**
     * Ejecutado antes de que se cargen los equipos y el contador.
     * @param args Argumentos extras.
     */
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

    /**
     * @param buf El buf que será enviado al cliente al detener el juego.
     * @return El mismo buf.
     */
    protected PacketByteBuf stopGameStateEventBuf(PacketByteBuf buf){
        return buf;
    }

    /**
     * Ejecutado después de que se inició el minijuego.
     */
    protected abstract void onStop();

    @Override
    public void reset() {
        cleared = true;
        stop();
        onReset();
        if (teamsInfo != null){
            players.forEach(teamsInfo::removeTeamFromPlayer);
        }
        PacketByteBuf buf = S2C_GameStateEventPacket.buf(this, S2C_GameStateEventPacket.RESET_EVENT);
        PacketByteBuf finalBuf = resetGameStateEventBuf(buf);
        players.forEach(player -> sendGameStateEventPacket(player, finalBuf));
    }

    /**
     * @param buf El buf que será enviado al cliente al resetear el juego.
     * @return El mismo buf.
     */
    protected PacketByteBuf resetGameStateEventBuf(PacketByteBuf buf){
        return buf;
    }

    /**
     * Ejecutado antes de que se resetee el minijuego.
     */
    protected abstract void onReset();

    private void sendGameStateEventPacket(PlayerEntity player, PacketByteBuf buf){
        ServerPlayNetworking.send((ServerPlayerEntity) player,
                        ServerPackets.S2C_GAME_STATE_EVENT.getIdentifier(), buf);
    }

    @Override
    public boolean isRunning() {
        return playing || !cleared;
    }

    @Override
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

    /**
     * @param player Un jugador al que se le añadirá el efecto de poción de Glowing
     */
    protected void addGlowing(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 0x989680, 0, false, false));
    }

    /**
     * @param player El jugador al que se le quitará el efecto de poción de Glowing.
     */
    protected void removeGlowing(PlayerEntity player){
        player.removeStatusEffect(StatusEffects.GLOWING);
    }

    protected Optional<PlayerTeams> getTeamOfPlayers(PlayerEntity source, PlayerEntity target){
        if (teamsInfo == null) return Optional.empty();
        Optional<Team> sourceTeamOpt = teamsInfo.getTeamOfPlayer(source);
        Optional<Team> targetTeamOpt = teamsInfo.getTeamOfPlayer(target);
        if (sourceTeamOpt.isEmpty() || targetTeamOpt.isEmpty()) return Optional.empty();
        Team sourceTeam = sourceTeamOpt.get();
        Team targetTeam = targetTeamOpt.get();
        return Optional.of(new PlayerTeams(sourceTeam, targetTeam));
    }

    protected record PlayerTeams(Team sourceTeam, Team targetTeam){

    }

}
