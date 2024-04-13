package me.julionxn.jueguitosclient.core.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.julionxn.jueguitosclient.core.teams.TeamColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlayerHeadButtonWidget extends ButtonWidget {

    private static final Identifier TEXTURE = new Identifier("jueguitosclient", "icon.png");
    private final PlayerListEntry entry;
    private final TeamColor teamColor;

    public PlayerHeadButtonWidget(int x, int y, int width, int height, PressAction onPress, PlayerListEntry entry, TeamColor teamColor) {
        super(x, y, width, height, Text.of(""), onPress, DEFAULT_NARRATION_SUPPLIER);
        this.entry = entry;
        this.teamColor = teamColor;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //todo: bug texto por debajo de las cabezas
        int x = getX();
        int y = getY();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderColor(teamColor.red, teamColor.green, teamColor.blue, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x - 1, y - 1, -10, 0, 0, width + 1, height + 1, width + 1, height + 1);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, entry.getSkinTexture());
        PlayerSkinDrawer.draw(matrices, getX(), getY(), width);
        if (isHovered()){
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null) return;
            TextRenderer textRenderer = client.textRenderer;
            drawTextWithShadow(matrices, textRenderer, entry.getProfile().getName(), mouseX + 6, mouseY, teamColor.color);
        }
    }


}
