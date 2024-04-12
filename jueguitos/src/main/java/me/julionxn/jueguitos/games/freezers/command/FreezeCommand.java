package me.julionxn.jueguitos.games.freezers.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.julionxn.jueguitos.games.freezers.networking.FreezersServerPackets;
import me.julionxn.jueguitos.games.freezers.networking.packets.S2C_SetFreezeStatePacket;
import me.julionxn.jueguitos.games.freezers.util.Freeze;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

public class FreezeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(
                CommandManager.literal("freeze").requires(source -> source.hasPermissionLevel(2)).then(
                        CommandManager.argument("targets", EntityArgumentType.players())
                                .then(
                                        CommandManager.literal("set").executes(ctx -> freeze(ctx, true))
                                )
                                .then(
                                        CommandManager.literal("unset").executes(ctx -> freeze(ctx, false))
                                )
                )
        );

    }

    private static int freeze(CommandContext<ServerCommandSource> ctx, boolean state) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(ctx, "targets");
        PacketByteBuf buf = S2C_SetFreezeStatePacket.buf(state);
        for (ServerPlayerEntity player : players) {
            ((Freeze) player).eufonia$setFreeze(state);
            ServerPlayNetworking.send(player, FreezersServerPackets.S2C_SET_FREEZE_STATE.getIdentifier(), buf);
        }
        return 1;
    }

}
