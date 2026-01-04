package fpl.excel.sheets;

import fpl.domain.model.PlayerSeasonView;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class GoalkeeperStatsSheetWriter
        extends TableSheetWriter<PlayerSeasonView> {

    private static final List<Col<PlayerSeasonView>> COLUMNS = List.of(
            new Col<>("Name", PlayerSeasonView::fullName),
            new Col<>("Points", PlayerSeasonView::totalPoints),
            new Col<>("Round", PlayerSeasonView::lastEventPoints),
            new Col<>("Bonus", PlayerSeasonView::totalBonus),
            new Col<>("PPM", PlayerSeasonView::pointsPerGame),
            new Col<>("Form", PlayerSeasonView::form),
            new Col<>("F_Rank", PlayerSeasonView::formRank),
            new Col<>("Min", PlayerSeasonView::minutes),
            new Col<>("Starts", PlayerSeasonView::starts),
            new Col<>("CleanS", PlayerSeasonView::cleanSheets),
            new Col<>("Saves", PlayerSeasonView::saves),
            new Col<>("PenSav", PlayerSeasonView::penaltiesSaved),
            new Col<>("Goal-", PlayerSeasonView::goalsConceded),
            new Col<>("xGC", PlayerSeasonView::xGC),
            new Col<>("GC-xGC", PlayerSeasonView::goalConcededAndXgcDelta),
            new Col<>("% sel", PlayerSeasonView::selectedByPercent),
            new Col<>("Cost", PlayerSeasonView::cost),
            new Col<>("Val(S)", PlayerSeasonView::valueSeason),
            new Col<>("Val(F)", PlayerSeasonView::valueForm),
            new Col<>("News", PlayerSeasonView::lastNews)
    );

    public GoalkeeperStatsSheetWriter(List<PlayerSeasonView> players) {
        super("Goalkeepers", players, COLUMNS);
    }
}
