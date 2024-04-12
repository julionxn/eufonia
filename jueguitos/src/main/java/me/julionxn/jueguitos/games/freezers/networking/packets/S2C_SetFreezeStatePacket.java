package me.julionxn.jueguitos.games.freezers.networking.packets;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.networking.S2CPacket;
import me.julionxn.jueguitos.games.freezers.networking.FreezersServerPackets;
import me.julionxn.jueguitos.games.freezers.util.Freeze;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class S2C_SetFreezeStatePacket implements S2CPacket {

    private static final Identifier ID = new Identifier(Jueguitos.ID, "set_freeze_state");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    public static PacketByteBuf buf(boolean state){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(state);
        return buf;
    }

    public static void setFreezeState(@NotNull PlayerEntity player, boolean state){
        ((Freeze) player).eufonia$setFreeze(state);
        ServerPlayNetworking.send((ServerPlayerEntity) player,
                FreezersServerPackets.S2C_SET_FREEZE_STATE.getIdentifier(),
                buf(state));
    }

}
