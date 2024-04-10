package me.julionxn.jueguitos.core.commands;

import me.julionxn.jueguitos.core.commands.cmds.GameCommand;
import me.julionxn.jueguitos.core.commands.cmds.PlayersCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class AllCommands {

    public static void register(){
           register(GameCommand::register);
           register(PlayersCommand::register);
    }

    private static void register(CommandRegistrationCallback callback){
        CommandRegistrationCallback.EVENT.register(callback);
    }

}
