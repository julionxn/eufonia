package me.julionxn.jueguitos.core.commands.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import me.julionxn.jueguitos.core.networking.ServerPackets;
import me.julionxn.jueguitos.core.networking.packets.S2C_OpenInfoScreenPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class GameCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(CommandManager.literal("game").requires(src -> src.hasPermissionLevel(2))
                .then(
                        CommandManager.literal("set")
                                .then(
                                        CommandManager.argument("gameId", StringArgumentType.string())
                                        .executes(ctx ->
                                            setActiveGame(ctx.getSource(), StringArgumentType.getString(ctx, "gameId"))
                                        )
                                )
                )
                .then(
                        CommandManager.literal("start").executes(ctx -> start(ctx.getSource(), null))
                )
                .then(
                        CommandManager.literal("start")
                                .then(
                                        CommandManager.argument("args", StringArgumentType.string())
                                        .executes(ctx -> start(ctx.getSource(), StringArgumentType.getString(ctx, "args"))
                                )
                        )
                )
                .then(
                        CommandManager.literal("stop").executes(ctx -> stop(ctx.getSource()))
                )
                .then(
                        CommandManager.literal("reset").executes(ctx -> reset(ctx.getSource()))
                )
                .then(
                        CommandManager.literal("info").executes(ctx -> info(ctx.getSource()))
                )

        );
    }

    private static int setActiveGame(ServerCommandSource src, String gameId){
        boolean success = GameStateManager.getInstance().setActiveMinigame(gameId);
        if (!success) {
            src.sendFeedback(Text.of("El juego con id '" + gameId + "' no se ha logrado establecer."), false);
            return 0;
        }
        src.sendFeedback(Text.of("El juego con id '" + gameId + "' se ha establecido."), true);
        return 1;
    }

    private static int start(ServerCommandSource src, @Nullable String args){
        boolean success = GameStateManager.getInstance().startGame(args);
        if (!success) {
            src.sendFeedback(Text.of("No se ha logrado empezar el juego."), false);
            return 0;
        }
        src.sendFeedback(Text.of("Se ha empezado el juego."), true);
        return 1;
    }

    private static int stop(ServerCommandSource src){
        boolean success = GameStateManager.getInstance().stopGame();
        if (!success) {
            src.sendFeedback(Text.of("No se ha logrado parar el juego."), false);
            return 0;
        }
        src.sendFeedback(Text.of("Se ha parado el juego."), true);
        return 1;
    }

    private static int reset(ServerCommandSource src){
        boolean success = GameStateManager.getInstance().resetGame();
        if (!success) {
            src.sendFeedback(Text.of("No se ha resetear parar el juego."), false);
            return 0;
        }
        src.sendFeedback(Text.of("Se ha reseteado el juego."), true);
        return 1;
    }

    private static int info(ServerCommandSource source){
        if (!source.isExecutedByPlayer()) return 0;
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) return 0;
        Minigame minigame = GameStateManager.getInstance().getActiveMinigame();
        if (minigame == null) return 0;
        PacketByteBuf buf = S2C_OpenInfoScreenPacket.buf(minigame);
        ServerPlayNetworking.send(player, ServerPackets.S2C_OPEN_INFO_SCREEN.getIdentifier(), buf);
        return 1;
    }

}
