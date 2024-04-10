package me.julionxn.jueguitos.core.commands.cmds;

import com.mojang.brigadier.CommandDispatcher;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class PlayersCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(
                CommandManager.literal("players").requires(src -> src.hasPermissionLevel(2))
                        .then(
                                CommandManager.literal("join")
                                        .then(
                                                CommandManager.argument("players", EntityArgumentType.players())
                                                .executes(ctx ->
                                                        joinPlayers(ctx.getSource(), EntityArgumentType.getPlayers(ctx, "players"))
                                                )
                                        )
                        )
                        .then(
                                CommandManager.literal("leave")
                                        .then(
                                                CommandManager.argument("players", EntityArgumentType.players())
                                                .executes(ctx ->
                                                        leavePlayers(ctx.getSource(), EntityArgumentType.getPlayers(ctx, "players"))
                                                )
                                        )
                        )
                        .then(
                                CommandManager.literal("clear")
                                .executes(ctx -> clearPlayers(ctx.getSource()))
                        )
        );

    }

    private static int joinPlayers(ServerCommandSource src, Collection<ServerPlayerEntity> targets){
        GameStateManager instance = GameStateManager.getInstance();
        for (ServerPlayerEntity target : targets) {
            instance.joinPlayer(target);
        }
        src.sendFeedback(Text.of("Se han añadido " + targets.size() + "jugador(es)."), true);
        return targets.size();
    }

    private static int leavePlayers(ServerCommandSource src, Collection<ServerPlayerEntity> targets){
        GameStateManager instance = GameStateManager.getInstance();
        for (ServerPlayerEntity target : targets) {
            instance.leavePlayer(target);
        }
        src.sendFeedback(Text.of("Se han eliminado " + targets.size() + "jugador(es)."), true);
        return targets.size();
    }

    private static int clearPlayers(ServerCommandSource src){
        GameStateManager.getInstance().clearPlayers();
        src.sendFeedback(Text.of("Se han eliminado todos los jugadores."), true);
        return 1;
    }

}
