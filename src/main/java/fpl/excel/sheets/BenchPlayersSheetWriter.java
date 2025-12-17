package fpl.excel.sheets;

import fpl.domain.model.SquadPlayer;
import fpl.domain.stats.PlayerGameweekStats;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class BenchPlayersSheetWriter extends TableSheetWriter<PlayerGameweekStats> {

    private static final List<Col<PlayerGameweekStats>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekStats::name),
            new Col<>("Count", PlayerGameweekStats::count),
            new Col<>("Bench", p -> p.count() - p.starts()),
            new Col<>("Points", PlayerGameweekStats::minPoints)
    );

    public BenchPlayersSheetWriter(List<PlayerGameweekStats> players) {
        super("Only Bench", players, COLUMNS);
    }
}
