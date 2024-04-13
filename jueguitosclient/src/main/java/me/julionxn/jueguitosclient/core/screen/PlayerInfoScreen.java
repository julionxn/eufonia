package me.julionxn.jueguitosclient.core.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.julionxn.jueguitosclient.core.networking.ClientPackets;
import me.julionxn.jueguitosclient.core.networking.packets.C2S_ChangeTeamPacket;
import me.julionxn.jueguitosclient.core.networking.packets.C2S_KillPlayerPacket;
import me.julionxn.jueguitosclient.core.screen.info.PlayersInfoScreen;
import me.julionxn.jueguitosclient.core.teams.Team;
import me.julionxn.jueguitosclient.core.teams.TeamColor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

public class PlayerInfoScreen extends Screen {

    private static final Identifier TEXTURE = new Identifier("jueguitosclient", "icon.png");
    private final String gameId;
    private final List<Team> teams;
    private Team currentTeam;
    private final Team originalTeam;
    private final PlayerListEntry entry;

    public PlayerInfoScreen(String gameId, PlayerListEntry entry, Team currentTeam, Set<Team> teams) {
        super(Text.of("PlayerInfoScreen"));
        this.gameId = gameId;
        this.entry = entry;
        this.currentTeam = currentTeam;
        this.originalTeam = currentTeam;
        this.teams = new ArrayList<>(teams);
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;
        int teamsAmount = teams.size();
        int y = centerY - (teamsAmount * 10);
        int offset = 0;
        for (Team team : teams) {
            ButtonWidget btn = ButtonWidget.builder(Text.of(team.id()), button -> {
                currentTeam = team;
                clearAndInit();
            }).dimensions(centerX, y + offset, 100, 20).build();
            if (team.id().equals(currentTeam.id())){
                btn.active = false;
            }
            offset += 20;
            addDrawableChild(btn);
        }
        ButtonWidget killBtn = ButtonWidget.builder(Text.of("Kill"), button -> {
            PacketByteBuf buf = C2S_KillPlayerPacket.buf(entry.getProfile().getId());
            ClientPlayNetworking.send(ClientPackets.C2S_KILL_PLAYER.getIdentifier(), buf);
        }).dimensions(centerX - 64, y + 52, 64, 20).build();
        addDrawableChild(killBtn);
        ButtonWidget copyBtn = ButtonWidget.builder(Text.of("Copy"), button -> {
            if (client == null) return;
            client.keyboard.setClipboard("/execute as " + entry.getProfile().getName() + " at @s run ");
        }).dimensions(centerX - 64, y + 72, 64, 20).build();
        addDrawableChild(copyBtn);
        ButtonWidget doneBtn = ButtonWidget.builder(Text.of("Listo"), button -> updateTeam())
                .dimensions(centerX - 64, y + 92, 64, 20).build();
        addDrawableChild(doneBtn);
    }

    @Override
    public void close() {
        super.close();
        updateTeam();
    }

    private void updateTeam(){
        UUID uuid = entry.getProfile().getId();
        if (!originalTeam.id().equals(currentTeam.id())){
            teams.stream().filter(team -> team.id().equals(originalTeam.id()))
                    .findFirst().ifPresent(team -> team.removePlayer(uuid));
            teams.stream().filter(team -> team.id().equals(currentTeam.id()))
                    .findFirst().ifPresent(team -> team.addPlayer(uuid));
            PacketByteBuf buf = C2S_ChangeTeamPacket.buf(currentTeam, uuid);
            ClientPlayNetworking.send(ClientPackets.C2S_CHANGE_TEAM.getIdentifier(), buf);
        }
        if (client == null) return;
        client.setScreen(new PlayersInfoScreen(gameId, new HashSet<>(teams)));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (client == null) return;
        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;
        TeamColor teamColor = currentTeam.teamColor();
        RenderSystem.setShaderColor(teamColor.red, teamColor.green, teamColor.blue, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, centerX - 64, centerY - 32, 64, 64, 0, 0, 64, 64, 64, 64);
        RenderSystem.setShaderColor(1f, 1f, 1f ,1f);
        RenderSystem.setShaderTexture(0, entry.getSkinTexture());
        PlayerSkinDrawer.draw(matrices, centerX - 62, centerY - 30, 60);
    }
}
