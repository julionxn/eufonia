package me.julionxn.jueguitos.core.teams;

import java.util.ArrayList;
import java.util.List;

public class TeamsSetup {

    protected final List<Team> teams = new ArrayList<>();

    public TeamsSetup addTeam(Team team){
        teams.add(team);
        return this;
    }

}
