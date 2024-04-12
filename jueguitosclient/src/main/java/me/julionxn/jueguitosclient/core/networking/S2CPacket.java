package me.julionxn.jueguitosclient.core.networking;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

/**
 * Interfaz que representa un paquete que es enviado desde el servidor al cliente.
 */
public interface S2CPacket {

    /**
     * @return El identificador del paquete.
     */
    Identifier getIdentifier();

    /**
     * @param client El client.
     * @param buf Paquete de bytes enviado desde el servidor.
     */
    void handlePacket(MinecraftClient client, PacketByteBuf buf);

}
