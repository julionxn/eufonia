package me.julionxn.jueguitosclient.core;

import me.julionxn.jueguitosclient.core.timer.BasicTimer;
import net.minecraft.network.PacketByteBuf;

/**
 * Representación abstracta de lo básico que requiere un minijuego para poder ser manejado
 * dentro del core.
 */
public interface ClientMinigame {

    /**
     * @return El id del minijuego.
     */
    String getId();

    /**
     * Usado para marcar como iniciado el juego.
     * @param buf El paquete recibido desde el servidor al iniciar el juego.
     */
    void start(PacketByteBuf buf);

    /**
     * Usado para marcar como detenido el juego.
     * @param buf El paquete recibido desde el servidor al detener el juego.
     */
    void stop(PacketByteBuf buf);

    /**
     * Usado para marcar como reseteado el juego.
     * @param buf El paquete recibido desde el servidor al resetear el juego.
     */
    void reset(PacketByteBuf buf);

    /**
     * Ejecutado cada tick.
     */
    default void tick(){}

    /**
     * @return Si el juego está siendo jugado.
     */
    boolean isRunning();

    /**
     * @return El contador básico de la instancia del juego.
     */
    BasicTimer getTimer();

}
