package me.julionxn.jueguitosclient.core.screen;

import me.julionxn.jueguitosclient.core.teams.Team;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Set;

public class InfoScreen extends Screen {

    private final String gameId;
    private final Set<Team> teams;
    private final int playersAmount;

    public InfoScreen(String gameId, Set<Team> teams) {
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
        super.init();
        if (client == null) return;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        final int BUTTON_SIZE = 16;
        final int BUTTON_SPAN = BUTTON_SIZE + 2;
        int columns = Math.min((width * 2 / 3) / BUTTON_SPAN, 10);
        int rows = (int) Math.floor((double) playersAmount / columns) + 1;
        int startingX = (width / 2) - (BUTTON_SPAN * columns / 2) - 1;
        int startingY = (height / 2) - (BUTTON_SPAN * rows / 2) - 1;
        int i = 0;
        for (Team team : teams) {
            for (PlayerListEntry entry : team.getEntries()) {
                int x = startingX + ((i % columns) * (BUTTON_SIZE + 2));
                int y = startingY + ((rows / Math.max(1, i)) * (BUTTON_SIZE + 2));
                ButtonWidget btn = new PlayerHeadButtonWidget(x, y, BUTTON_SIZE, BUTTON_SIZE, button ->
                        client.setScreen(new PlayerInfoScreen(gameId, entry, team, teams))
                        , entry, team.teamColor());
                i++;
                addDrawableChild(btn);
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (client == null) return;
        int x = client.getWindow().getScaledWidth() / 2;
        drawCenteredTextWithShadow(matrices, client.textRenderer, gameId, x, 20, 0xffffff);
    }

}
