package me.julionxn.jueguitosclient.core.networking;

import net.minecraft.util.Identifier;

/**
 * Interfaz que representa un paquete que es enviado desde el client al servidor.
 */
public interface C2SPacket {

    /**
     * @return El identificador del paquete.
     */
    Identifier getIdentifier();

}
