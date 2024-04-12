package me.julionxn.jueguitos.core.networking.packets;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.networking.S2CPacket;
import me.julionxn.jueguitos.core.timer.BasicTimer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

/**
 * Paquete usado para avisar a los clientes un evento de estado de los minijuegos.
 */
public class S2C_GameStateEventPacket implements S2CPacket {

    private static final Identifier ID = new Identifier(Jueguitos.ID, "game_state_event");
    public static final int CHANGE_GAME_EVENT = -1;
    public static final int START_EVENT = 0;
    public static final int STOP_EVENT = 1;
    public static final int RESET_EVENT = 2;

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    /**
     * @param game La instancia del minijuego.
     * @param event El entero al que corresponde el evento.
     * @return El correspondiente paquete.
     * @see S2C_GameStateEventPacket Ver los enteros a los cuales corresponden los eventos.
     */
    public static PacketByteBuf buf(Minigame game, int event){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(game.getId());
        buf.writeInt(event);
        BasicTimer timer = game.getTimer();
        if (timer != null){
            buf.writeLong(timer.getLastTimeSpan());
            buf.writeLong(timer.getRemainingMills());
        } else {
            buf.writeLong(-1);
            buf.writeLong(-1);
        }
        return buf;
    }

}
