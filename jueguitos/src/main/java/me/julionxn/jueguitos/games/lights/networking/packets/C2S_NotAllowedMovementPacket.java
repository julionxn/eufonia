package me.julionxn.jueguitos.games.lights.networking.packets;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.Minigame;
import me.julionxn.jueguitos.core.managers.GameStateManager;
import me.julionxn.jueguitos.core.networking.C2SPacket;
import me.julionxn.jueguitos.games.lights.LightsGame;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class C2S_NotAllowedMovementPacket implements C2SPacket {

    private static final Identifier ID = new Identifier(Jueguitos.ID, "not_allowed_movement");

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void handlePacket(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf) {
        server.execute(() -> {
            Minigame minigame = GameStateManager.getInstance().getActiveMinigame();
            if (minigame instanceof LightsGame game){
                player.sendMessage(Text.of("YOU MOVED"));
                game.markAsMoved(player);
            }
        });
    }

}
