package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekStats;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class DoubtfulPlayersSheetWriter extends TableSheetWriter<PlayerGameweekStats> {

    private static final List<Col<PlayerGameweekStats>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekStats::name),
            new Col<>("Count", PlayerGameweekStats::count),
            new Col<>("Start", PlayerGameweekStats::starts),
            new Col<>("AV", PlayerGameweekStats::availability),
            new Col<>("Points", PlayerGameweekStats::minPoints)
    );

    public DoubtfulPlayersSheetWriter(List<PlayerGameweekStats> players) {
        super("Doubtful", players, COLUMNS);
    }
}
