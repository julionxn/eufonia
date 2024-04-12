package me.julionxn.jueguitos.core.teams;

import me.julionxn.jueguitos.Jueguitos;
import me.julionxn.jueguitos.core.teams.distribution.DistributionType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Representa ya los equipos formados dentro de un minijuego.
 */
public class TeamsInfo {

    private static final Random RANDOM = new Random();
    private Set<Team> teams = new HashSet<>();
    private Set<Team> fixedDistTeams = new HashSet<>();
    private Set<Team> remainingDistTeams = new HashSet<>();
    private final HashMap<UUID, Team> players = new HashMap<>();

    public TeamsInfo(TeamsSetup setup){
        setup(setup);
    }

    /**
     * Se encarga de distribuir los equipos de acuerdo a su tipo de distribución,
     * además de que verifica que no vengan equipos con colores o ids duplicados.
     * @param setup Un objeto de la clase TeamsSetup
     */
    private void setup(TeamsSetup setup){
        teams = new HashSet<>(setup.teams);
        Set<String> teamsIds = new HashSet<>();
        Set<TeamColor> pickedColors = new HashSet<>();
        fixedDistTeams = teams.stream()
                .filter(team -> team.distribution().distributionType() == DistributionType.FIXED)
                .collect(Collectors.toSet());
        remainingDistTeams = teams.stream()
                .filter(team -> team.distribution().distributionType() == DistributionType.REMAINING)
                .collect(Collectors.toSet());
        for (Team team : teams) {
            String teamId = team.id();
            teamsIds.add(teamId);
            TeamColor color = team.teamColor();
            pickedColors.add(color);
        }
        if (teams.size() != pickedColors.size()){
            Jueguitos.LOGGER.error("Un color ha sido seleccionado para varios equipos multiples veces.");
        }
        if (teams.size() != teamsIds.size()){
            Jueguitos.LOGGER.error("Una misma id ha sido aplicada a más de un solo equipo. Debería de ser única");
        }
    }

    /**
     * Se encarga de distribuir a los jugadores en sus respectivos equipos de acuerdo
     * a los parámetros de distribución de los equipos registrados en el setup.
     * En caso de que el número de jugadores sea menor a la cantidad necesaria para distribuirlos
     * en los equipos con distribución FIXED, se distribuyen a los jugadores entre los equipos
     * como si todos estos fueran con una distribución REMAINING
     * @param players La lista con los jugadores a los cuales se les van a aplicar los equipos
     */
    public void setupPlayers(List<PlayerEntity> players){
        List<PlayerEntity> playersLeft = new ArrayList<>(players);
        for (Team team : fixedDistTeams) {
            int size = team.distribution().size();
            for (int i = 0; i < size; i++) {
                if (playersLeft.isEmpty()){
                    setupPlayersOnOverflow(players);
                    return;
                }
                addRandomPlayerToTeam(playersLeft, team);
            }
        }
        int remainingPlayers = playersLeft.size();
        if (remainingPlayers == 0) {
            setupPlayersOnOverflow(players);
            return;
        }
        int playersPerTeam = remainingPlayers / remainingDistTeams.size();
        int playersOverflow = remainingPlayers - (remainingDistTeams.size() * playersPerTeam);
        for (Team team : remainingDistTeams) {
            for (int i = 0; i < playersPerTeam; i++) {
                addRandomPlayerToTeam(playersLeft, team);
            }
            if (playersOverflow > 0){
                addRandomPlayerToTeam(playersLeft, team);
                playersOverflow--;
            }
        }
    }

    /**
     * Se distribuyen a todos los jugadores entre todos los equipos, como si todos estos fueran
     * establecidos con una distribución REMAINING
     * @param players La lista con los jugadores a los cuales se les van a aplicar los equipos
     */
    private void setupPlayersOnOverflow(List<PlayerEntity> players){
        Set<Team> allTeams = new HashSet<>(remainingDistTeams);
        allTeams.addAll(fixedDistTeams);
        int teamsAmount = allTeams.size();
        int size = players.size();
        int playersPerTeam = size / teamsAmount;
        int playersOverflow = size - (playersPerTeam * teamsAmount);
        for (Team team : allTeams){
            for (int i = 0; i < playersPerTeam; i++) {
                addRandomPlayerToTeam(players, team);
            }
            if (playersOverflow > 0){
                addRandomPlayerToTeam(players, team);
                playersOverflow--;
            }
        }
    }

    /**
     * Se añade a un jugador aleatorio de la lista al equipo pasado como argumento
     * @param players La lista de jugadores
     * @param team El equipo al cual se añadirá
     */
    private void addRandomPlayerToTeam(List<PlayerEntity> players, Team team){
        int randomIndex = getRandomIndex(players);
        PlayerEntity pickedPlayer = players.get(randomIndex);
        addPlayerToTeam(pickedPlayer, team);
        players.remove(randomIndex);
    }

    /**
     * Se añade un jugador a un equipo. En caso de que el jugador ya esté en otro equipo,
     * se sobreescribe.
     * @param player El jugador
     * @param team El equipo
     */
    public void addPlayerToTeam(PlayerEntity player, Team team){
        this.players.put(player.getUuid(), team);
        String teamId = team.id();
        player.addCommandTag(teamId);
    }

    public void removeTeamFromPlayer(PlayerEntity player){
        Team team = this.players.remove(player.getUuid());
        if (team == null) return;
        player.removeScoreboardTag(team.id());
    }

    /**
     * @param players La lista de jugadores
     * @return Un índice al azar de la lista de jugadores
     */
    private int getRandomIndex(List<PlayerEntity> players){
        return RANDOM.nextInt(players.size());
    }

    /**
     * @param player El jugador
     * @return El equipo al cual pertenece el jugador.
     */
    public Optional<Team> getTeamOfPlayer(PlayerEntity player){
        return Optional.ofNullable(players.get(player.getUuid()));
    }

    /**
     * @param id del equipo
     * @return Si existe, el equipo al cual corresponde el id.
     */
    public Optional<Team> getTeam(String id){
        return teams.stream().filter(team -> team.id().equals(id)).findFirst();
    }

    /**
     * @return Todos los equipos.
     */
    public Set<Team> getTeams(){
        return teams;
    }

    /**
     * @return Un mapa relacionado entre los UUIDS de los jugadores y se respectivo equipo
     */
    public HashMap<UUID, Team> getPlayers(){
        return players;
    }

    /**
     * @param players El conjunto de jugadores
     * @param team El equipo
     * @return Todos los jugadores dentro del conjunto de jugadores pasado como argumento que se encuentren
     * dentro del equipo.
     */
    public Set<PlayerEntity> getPlayersInTeam(Set<PlayerEntity> players, Team team){
        return getPlayersInTeam(players, team.id());
    }

    /**
     * @param players El conjunto de jugadores
     * @param id El id del equipo
     * @return Todos los jugadores dentro del conjunto de jugadores pasado como argumento que se encuentren
     * dentro del equipo al cual corresponde el id.
     */
    public Set<PlayerEntity> getPlayersInTeam(Set<PlayerEntity> players, String id){
        return players.stream()
                .filter(player -> this.players.get(player.getUuid()).id().equals(id))
                .collect(Collectors.toSet());
    }

    /**
     * Resetear todos los equipos.
     */
    public void resetTeams(){
        teams.clear();
        players.clear();
    }

}
