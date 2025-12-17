package fpl.domain.service;

import fpl.api.FplApiEndPoints;
import fpl.api.InputUtils;
import fpl.api.dto.EntryInfo;
import fpl.api.dto.LeagueResponse;
import fpl.domain.repository.LeagueRepository;

import java.util.ArrayList;
import java.util.List;

public class StandingsParsingService {

    private StandingsParsingService() {}

    public static List<Integer> collectTeamIds(LeagueRepository leagueRepository, int mode) {
        List<Integer> result = new ArrayList<>();
        int totalPages = mode <= InputUtils.MAX_PAGES ? mode : 1;

        for (int page = 1; page <= totalPages; page++) {
            LeagueResponse response = leagueRepository.getLeaguePage(FplApiEndPoints.getLeagueId(mode), page);

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
