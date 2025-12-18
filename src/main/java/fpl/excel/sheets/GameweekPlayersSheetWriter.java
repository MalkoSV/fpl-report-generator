package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekSummary;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class GameweekPlayersSheetWriter extends TableSheetWriter<PlayerGameweekSummary> {

    private static final List<Col<PlayerGameweekSummary>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekSummary::name),
            new Col<>("Count", PlayerGameweekSummary::count),
            new Col<>("Start", PlayerGameweekSummary::starts),
            new Col<>("Captain", PlayerGameweekSummary::captains),
            new Col<>("Triple", PlayerGameweekSummary::tripleCaptains),
            new Col<>("Vice", PlayerGameweekSummary::viceCaptains),
            new Col<>("Bench", p -> p.count() - p.starts()),
            new Col<>("AV", PlayerGameweekSummary::availability),
            new Col<>("Points", PlayerGameweekSummary::minPoints)
    );

    public GameweekPlayersSheetWriter(List<PlayerGameweekSummary> players) {
        super("Gameweek players", players, COLUMNS);
    }
}
