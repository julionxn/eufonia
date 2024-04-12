package me.julionxn.jueguitos.core.networking;

import net.minecraft.util.Identifier;

/**
 * Interfaz que representa un paquete que es enviado desde el cliente al servidor.
 */
public interface S2CPacket {

    /**
     * @return El identificador del paquete.
     */
    Identifier getIdentifier();

}
