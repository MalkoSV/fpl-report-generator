package fpl.output;

import fpl.domain.model.PlayerSeasonView;
import fpl.domain.transfers.Transfer;
import fpl.excel.core.ExcelWriter;
import fpl.excel.io.FileNameGenerator;
import fpl.excel.io.WorkbookFactory;
import fpl.excel.sheets.HighPointsBenchSheetWriter;
import fpl.excel.sheets.CaptainPlayersSheetWriter;
import fpl.excel.sheets.DoubtfulPlayersSheetWriter;
import fpl.excel.sheets.GameweekPlayersSheetWriter;
import fpl.excel.sheets.BenchPlayersSheetWriter;
import fpl.excel.sheets.StartPlayersSheetWriter;
import fpl.excel.sheets.PlayerStatsSheetWriter;
import fpl.excel.sheets.SummarySheetWriter;
import fpl.domain.model.Team;
import fpl.excel.sheets.TransfersSheetWriter;
import fpl.output.builder.ReportDataBuilder;
import fpl.output.model.ReportData;

import java.util.List;

public class ReportExportService {

    private final ReportDataBuilder dataBuilder = new ReportDataBuilder();

    public void exportResults(
            List<Team> teams,
            List<PlayerSeasonView> playersData,
            List<Transfer> transfers,
            int event,
            String[] args) {

        ReportData reportData = dataBuilder.build(teams, playersData, transfers);

        ExcelWriter writer = new ExcelWriter(
                new WorkbookFactory(),
                new OutputDirectoryResolver(),
                new FileNameGenerator()
        );

        writer.writeExcel(
                "FPL Report GW-%d (top %d)".formatted(event, teams.size()),
                args,
                new GameweekPlayersSheetWriter(reportData.allPlayers()),
                new CaptainPlayersSheetWriter(reportData.captains()),
                new StartPlayersSheetWriter(reportData.starters()),
                new BenchPlayersSheetWriter(reportData.bench()),
                new DoubtfulPlayersSheetWriter(reportData.doubtful()),
                new HighPointsBenchSheetWriter(reportData.highPointsBench()),
                new SummarySheetWriter(reportData.summaryData()),
                new TransfersSheetWriter(reportData.transfersData()),
                new PlayerStatsSheetWriter(reportData.topSeasonPlayers())
        );
    }
}
