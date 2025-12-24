package fpl.excel.sheets;

import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;
import fpl.domain.model.PlayerSeasonView;

import java.util.List;

public class PlayerStatsSheetWriter extends TableSheetWriter<PlayerSeasonView> {

    private static final List<Col<PlayerSeasonView>> COLUMNS = List.of(
            new Col<>("Name", PlayerSeasonView::fullName),
            new Col<>("Points", PlayerSeasonView::totalPoints),
            new Col<>("PPM", PlayerSeasonView::pointsPerGame),
            new Col<>("Form", PlayerSeasonView::form),
            new Col<>("F_Rank", PlayerSeasonView::formRank),
            new Col<>("Round", PlayerSeasonView::lastEventPoints),
            new Col<>("Rnd-F", PlayerSeasonView::eventPointsAndFormDelta),
            new Col<>("Bonus", PlayerSeasonView::totalBonus),
            new Col<>("Min", PlayerSeasonView::minutes),
            new Col<>("Starts", PlayerSeasonView::starts),
            new Col<>("CleanS", PlayerSeasonView::cleanSheets),
            new Col<>("DC", PlayerSeasonView::defensiveContribution),
            new Col<>("DC(90)", PlayerSeasonView::defensiveContributionPer90),
            new Col<>("G", PlayerSeasonView::goalsScored),
            new Col<>("A", PlayerSeasonView::assists),
            new Col<>("G+A", PlayerSeasonView::goalsPlusAssists),
            new Col<>("xG", PlayerSeasonView::xG),
            new Col<>("xA", PlayerSeasonView::xA),
            new Col<>("xGI", PlayerSeasonView::xGI),
            new Col<>("GA-xGI", PlayerSeasonView::realityAndXgiDelta),
            new Col<>("#Pen", PlayerSeasonView::penaltiesOrder),
            new Col<>("#Kick", PlayerSeasonView::directFreekicksOrder),
            new Col<>("#Corn", PlayerSeasonView::otherFreekicksOrder),
            new Col<>("% sel", PlayerSeasonView::selectedByPercent),
            new Col<>("Cost", PlayerSeasonView::cost),
            new Col<>("Val(S)", PlayerSeasonView::valueSeason),
            new Col<>("Val(F)", PlayerSeasonView::valueForm),
            new Col<>("News", PlayerSeasonView::lastNews)
    );

    public PlayerStatsSheetWriter(List<PlayerSeasonView> players) {
        super("Player Stats", players, COLUMNS);
    }
}
