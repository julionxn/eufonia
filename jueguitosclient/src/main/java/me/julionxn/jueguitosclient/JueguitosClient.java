package me.julionxn.jueguitosclient;

import me.julionxn.jueguitosclient.core.networking.ClientPackets;
import me.julionxn.jueguitosclient.games.AllClientGames;
import me.julionxn.jueguitosclient.games.freezers.FreezersClientGame;
import me.julionxn.jueguitosclient.games.freezers.networking.FreezersClientPackets;
import me.julionxn.jueguitosclient.games.lights.networking.LightsClientPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
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
		ClientPlayConnectionEvents.JOIN.register(FreezersClientGame::onJoin);
		LOGGER.info("Hello Fabric world!");

		LightsClientPackets.register();
	}
}