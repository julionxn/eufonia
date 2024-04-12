package me.julionxn.jueguitos.core;

import me.julionxn.jueguitos.core.teams.TeamsInfo;
import me.julionxn.jueguitos.core.teams.TeamsSetup;
import me.julionxn.jueguitos.core.timer.BasicTimer;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Set;

/**
 * Representación abstracta de lo básico que requiere un minijuego para poder ser manejado
 * dentro del core.
 */
public interface Minigame {

    /**
     * @return El id del minijuego.
     */
    String getId();

    /**
     * @param teamsSetup Instancia de TeamSetup usada para registrar los equipos que serán usados
     *                   dentro de este minijuego.
     * @return La misma instancia que se pasó
     */
    @NotNull TeamsSetup teamsSetup(TeamsSetup teamsSetup);


    /**
     * Ejecutado al iniciar el mod. Util para cargar configuraciones.
     */
    default void onModInitialization(){}


    /**
     * Iniciar el minijuego.
     * @param players Los jugadores que estarán dentro de este minijuego.
     * @param args Argumentos extra.
     * @return Si la operación fue exitosa.
     */
    boolean start(Set<PlayerEntity> players, @Nullable HashMap<String, String> args);

    /**
     * Detener el minijuego.
     * @return Si la operación fue exitosa.
     */
    boolean stop();

    /**
     * Resetear el minijuego.
     */
    void reset();

    /**
     * Ejecutado cada tick.
     */
    default void tick(){}


    /**
     * @return Si el minijuego está siendo jugado o no ha sido reseteado después de iniciarse.
     */
    boolean isRunning();

    /**
     * @return Si el minijuego está siendo jugado.
     */
    boolean isPlaying();

    /**
     * @return La instancia básica de contador del minijuego.
     */
    @Nullable BasicTimer getTimer();


    /**
     * @return Los equipos ya cargados de la instancia.
     */
    @Nullable TeamsInfo getTeamsInfo();


    /**
     * Ejecutado cuando un jugador golpea a otro.
     * @param source Quien pegó.
     * @param target A quien le pegaron.
     */
    void onPlayerHitAnother(PlayerEntity source, PlayerEntity target);

}
