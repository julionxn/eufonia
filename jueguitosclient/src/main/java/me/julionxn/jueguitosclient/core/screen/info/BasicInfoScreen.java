package me.julionxn.jueguitosclient.core.screen.info;

import me.julionxn.jueguitosclient.core.ClientMinigame;
import me.julionxn.jueguitosclient.core.managers.ClientGameStateManager;
import me.julionxn.jueguitosclient.core.teams.Team;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Set;

public abstract class BasicInfoScreen extends Screen {

    protected final String gameId;
    protected final Set<Team> teams;
    protected final int playersAmount;

    public BasicInfoScreen(String gameId, Set<Team> teams) {
        super(Text.of("InfoScreen"));
        this.gameId = gameId;
        this.teams = teams;
        int playersAmount = 0;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient != null) {
            for (Team team : teams) {
                team.setupPlayers(minecraftClient);
                playersAmount += team.getUuids().size();
            }
        }
        this.playersAmount = playersAmount;
    }

    @Override
    protected void init() {
        if (client == null) return;
        int centerX = client.getWindow().getScaledWidth() / 2;
        ButtonWidget teamsBtn = ButtonWidget.builder(Text.of("teams"), button -> {
            client.setScreen(new TeamsInfoScreen(gameId, teams));
        }).dimensions(centerX - 120, 10, 120, 20).build();
        addDrawableChild(teamsBtn);
        ButtonWidget playersBtn = ButtonWidget.builder(Text.of("players"), button -> {
            client.setScreen(new PlayersInfoScreen(gameId, teams));
        }).dimensions(centerX, 10, 120, 20).build();
        addDrawableChild(playersBtn);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (client == null) return;
        int x = client.getWindow().getScaledWidth() / 2;
        String info = gameId + ": " +
                "teams -> " + teams.size() + ", " +
                "players -> " + playersAmount + ", ";
        ClientMinigame minigame = ClientGameStateManager.getInstance().getActiveMinigame();
        if (minigame != null){
            info = info + "running: " + minigame.isRunning();
        }
        drawCenteredTextWithShadow(matrices, client.textRenderer, info, x, 30, 0xffffff);
    }

}
