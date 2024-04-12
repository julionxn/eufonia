package me.julionxn.jueguitos;

import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.commands.AllCommands;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import me.julionxn.jueguitos.core.networking.ServerPackets;
import me.julionxn.jueguitos.games.AllGames;
import me.julionxn.jueguitos.games.freezers.command.FreezeCommand;
import me.julionxn.jueguitos.games.freezers.networking.FreezersServerPackets;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Jueguitos implements DedicatedServerModInitializer {

	public static final String ID = "jueguitos";
    public static final Logger LOGGER = LoggerFactory.getLogger("jueguitos");

	@Override
	public void onInitializeServer() {
		System.out.println("RUNNING BABYYYYYYYYYYYYYYYYYYYYYYY");
		ServerPackets.register();
		AllGames.register();
		AllCommands.register();
		GameStateManager.getInstance().getRegisteredGames().values().forEach(Minigame::onModInitialization);

		//Games
		FreezersServerPackets.register();
		CommandRegistrationCallback.EVENT.register(FreezeCommand::register);
	}

}