package fpl.api.model;

import java.util.List;

public record StandingsContainer(
        List<TeamStats> results
) {}
