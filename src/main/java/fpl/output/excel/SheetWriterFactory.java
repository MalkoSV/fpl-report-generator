package fpl.output.excel;

import fpl.excel.sheets.BenchPlayersSheetWriter;
import fpl.excel.sheets.CaptainPlayersSheetWriter;
import fpl.excel.sheets.DoubtfulPlayersSheetWriter;
import fpl.excel.sheets.GameweekPlayersSheetWriter;
import fpl.excel.sheets.HighPointsBenchSheetWriter;
import fpl.excel.sheets.PlayerStatsSheetWriter;
import fpl.excel.core.SheetWriter;
import fpl.excel.sheets.StartPlayersSheetWriter;
import fpl.excel.sheets.SummarySheetWriter;
import fpl.excel.sheets.TransfersSheetWriter;
import fpl.output.model.ReportData;

import java.util.List;

public class SheetWriterFactory {

    public List<SheetWriter> create(ReportData data) {
        return List.of(
                new GameweekPlayersSheetWriter(data.allPlayers()),
                new CaptainPlayersSheetWriter(data.captains()),
                new StartPlayersSheetWriter(data.starters()),
                new BenchPlayersSheetWriter(data.bench()),
                new DoubtfulPlayersSheetWriter(data.doubtful()),
                new HighPointsBenchSheetWriter(data.highPointsBench()),

                new SummarySheetWriter(data.summaryData()),
                new TransfersSheetWriter(data.transfersData()),

                new PlayerStatsSheetWriter(data.topSeasonPlayers())
        );
    }
}
