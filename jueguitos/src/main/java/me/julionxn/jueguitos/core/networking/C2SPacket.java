package me.julionxn.jueguitos.core.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Interfaz que representa un paquete que es enviado desde el client al servidor.
 */
public interface C2SPacket {

    /**
     * @return El identificador del paquete.
     */
    Identifier getIdentifier();

    /**
     * @param server El server
     * @param player El jugador correspondiente que ha enviado el paquete desde el client al servidor.
     * @param buf Paquete de bytes enviado desde el servidor.
     */
    void handlePacket(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf);

}
