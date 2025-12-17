package fpl.domain.repository;

import fpl.api.dto.LeagueResponse;

public interface LeagueRepository {
    LeagueResponse getLeaguePage(int leagueId, int page);
}
