package fpl.repository;

import fpl.api.FplApiClient;
import fpl.api.dto.LeagueResponse;
import fpl.domain.repository.LeagueRepository;

public class ApiLeagueRepository implements LeagueRepository {

    private final FplApiClient api;

    public ApiLeagueRepository(FplApiClient api) {
        this.api = api;
    }

    @Override
    public LeagueResponse getLeaguePage(int leagueId, int page) {
        return api.getLeaguePage(leagueId, page);
    }
}
