package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekStats;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class StartPlayersSheetWriter extends TableSheetWriter<PlayerGameweekStats> {

    private static final List<Col<PlayerGameweekStats>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekStats::name),
            new Col<>("Count", PlayerGameweekStats::count),
            new Col<>("Start", PlayerGameweekStats::starts),
            new Col<>("Points", PlayerGameweekStats::minPoints)
    );

    public StartPlayersSheetWriter(List<PlayerGameweekStats> players) {
        super("Only start", players, COLUMNS);
    }
}
