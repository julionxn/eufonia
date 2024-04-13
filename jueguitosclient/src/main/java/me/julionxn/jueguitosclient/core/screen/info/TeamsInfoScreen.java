package me.julionxn.jueguitosclient.core.screen.info;

import me.julionxn.jueguitosclient.core.teams.Team;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.Set;

public class TeamsInfoScreen extends BasicInfoScreen{

    public TeamsInfoScreen(String gameId, Set<Team> teams) {
        super(gameId, teams);
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        int centerX = client.getWindow().getScaledWidth() / 2;
        int offset = 0;
        for (Team team : teams) {
            ButtonWidget btn = ButtonWidget.builder(Text.of(team.id() + ": " + team.getEntries().size()), button ->
                client.keyboard.setClipboard(
                        "/execute as @a[tag=" + team.id() + "] at @s run "
                )
            ).dimensions(centerX - 200, 60 + offset, 400, 20).build();
            offset += 20;
            addDrawableChild(btn);
        }
    }
}
