package me.julionxn.jueguitosclient.core;

import me.julionxn.jueguitosclient.core.timer.BasicTimer;
import net.minecraft.network.PacketByteBuf;

/**
 * Clase usada como base para crear minijuegos básicos del lado del cliente.
 */
public abstract class BasicClientMinigame implements ClientMinigame {

    private boolean running;
    protected final BasicTimer timer = new BasicTimer(-1, -1);

    @Override
    public void start(PacketByteBuf buf) {
        running = true;
        onStart(buf);
    }

    /**
     * Ejecutado cuando el juego es iniciado.
     * @param buf Paquete recibido desde el servidor cuando se inicia el minijuego.
     */
    protected abstract void onStart(PacketByteBuf buf);

    @Override
    public void stop(PacketByteBuf buf) {
        running = false;
        onStop(buf);
    }

    /**
     * Ejecutado cuando el juego es detenido.
     * @param buf Paquete recibido desde el servidor cuando se detiene el minijuego.
     */
    protected abstract void onStop(PacketByteBuf buf);

    @Override
    public void reset(PacketByteBuf buf) {
        running = false;
        onReset(buf);
    }

    /**
     * Ejecutado cuando el juego es reseteado.
     * @param buf Paquete recibido desde el servidor cuando se resetea el minijuego.
     */
    protected abstract void onReset(PacketByteBuf buf);

    /**
     * @return El BasicTimer del minijuego.
     */
    @Override
    public BasicTimer getTimer() {
        return timer;
    }

    /**
     * @return Si el jugado está siendo jugado, es decir que no está detenido.
     */
    @Override
    public boolean isRunning() {
        return running;
    }

}
