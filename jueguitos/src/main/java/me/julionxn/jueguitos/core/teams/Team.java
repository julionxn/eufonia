package me.julionxn.jueguitos.core.teams;

import me.julionxn.jueguitos.core.teams.distribution.Distribution;

/**
 * @param id La id del equipo
 * @param teamColor El color correspondiente del equipo
 * @param distribution La forma en que se distribuirán los jugadores a este equipo.
 */
public record Team(String id, TeamColor teamColor, Distribution distribution) {
}
