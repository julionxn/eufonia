package me.julionxn.jueguitosclient;

import me.julionxn.jueguitosclient.core.networking.ClientPackets;
import me.julionxn.jueguitosclient.games.AllClientGames;
import me.julionxn.jueguitosclient.games.freezers.networking.FreezersClientPackets;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JueguitosClient implements ClientModInitializer {

	public static final String ID = "jueguitos";
    public static final Logger LOGGER = LoggerFactory.getLogger("jueguitosclient");

	@Override
	public void onInitializeClient() {
		ClientPackets.register();
		AllClientGames.register();

		//Games
		FreezersClientPackets.register();
		LOGGER.info("Hello Fabric world!");
	}
}