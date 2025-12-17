package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekStats;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class GameweekPlayersSheetWriter extends TableSheetWriter<PlayerGameweekStats> {

    private static final List<Col<PlayerGameweekStats>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekStats::name),
            new Col<>("Count", PlayerGameweekStats::count),
            new Col<>("Start", PlayerGameweekStats::starts),
            new Col<>("Captain", PlayerGameweekStats::captains),
            new Col<>("Triple", PlayerGameweekStats::tripleCaptains),
            new Col<>("Vice", PlayerGameweekStats::viceCaptains),
            new Col<>("Bench", p -> p.count() - p.starts()),
            new Col<>("AV", PlayerGameweekStats::availability),
            new Col<>("Points", PlayerGameweekStats::minPoints)
    );

    public GameweekPlayersSheetWriter(List<PlayerGameweekStats> players) {
        super("Gameweek players", players, COLUMNS);
    }
}
