package fpl.domain.usecase;

import fpl.api.dto.EntryInfo;
import fpl.api.dto.LeagueResponse;
import fpl.domain.repository.LeagueRepository;

import java.util.ArrayList;
import java.util.List;

public class FetchTeamIdsUseCase {

    private final LeagueRepository leagueRepository;

    public FetchTeamIdsUseCase(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public List<Integer> execute(int leagueId, int pages) {
        List<Integer> result = new ArrayList<>();

        for (int page = 1; page <= pages; page++) {
            LeagueResponse response = leagueRepository.getLeaguePage(leagueId, page);

            result.addAll(
                    response.standings().results()
                            .stream()
                            .map(EntryInfo::entry)
                            .toList()
            );
        }
        return result;
    }
}
