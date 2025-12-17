package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekStats;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class CaptainPlayersSheetWriter extends TableSheetWriter<PlayerGameweekStats> {

    private static final List<Col<PlayerGameweekStats>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekStats::name),
            new Col<>("Captain", PlayerGameweekStats::captains),
            new Col<>("Points", player -> player.minPoints() * 2)
    );

    public CaptainPlayersSheetWriter(List<PlayerGameweekStats> players) {
        super("Captain", players, COLUMNS);
    }
}
