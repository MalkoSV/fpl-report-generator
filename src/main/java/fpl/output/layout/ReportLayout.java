package fpl.output.layout;

import fpl.excel.core.SheetWriter;
import fpl.excel.sheets.BenchPlayersSheetWriter;
import fpl.excel.sheets.CaptainPlayersSheetWriter;
import fpl.excel.sheets.DoubtfulPlayersSheetWriter;
import fpl.excel.sheets.GameweekPlayersSheetWriter;
import fpl.excel.sheets.GoalkeeperStatsSheetWriter;
import fpl.excel.sheets.HighPointsBenchSheetWriter;
import fpl.excel.sheets.PlayerStatsSheetWriter;
import fpl.excel.sheets.StartPlayersSheetWriter;
import fpl.excel.sheets.SummarySheetWriter;
import fpl.excel.sheets.TransfersSheetWriter;
import fpl.output.model.ReportData;

import java.util.List;

public class ReportLayout {
    public List<SheetWriter> buildSheets(ReportData data) {
        var gameweek = data.gameweek();

        return List.of(
                new GameweekPlayersSheetWriter(gameweek.all()),
                new CaptainPlayersSheetWriter(gameweek.captains()),
                new StartPlayersSheetWriter(gameweek.starters()),
                new BenchPlayersSheetWriter(gameweek.bench()),
                new DoubtfulPlayersSheetWriter(gameweek.doubtful()),
                new HighPointsBenchSheetWriter(gameweek.highPointsBench()),

                new SummarySheetWriter(data.summaryData()),
                new TransfersSheetWriter(data.transfersData()),

                new PlayerStatsSheetWriter(data.topSeasonPlayers()),
                new GoalkeeperStatsSheetWriter(data.seasonGoalkeepers())
        );
    }
}
