package fpl.output;

import fpl.domain.model.PlayerSeasonView;
import fpl.domain.transfers.Transfer;
import fpl.excel.core.ExcelWriter;
import fpl.excel.io.FileNameGenerator;
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

import java.io.File;
import java.util.List;

public class ReportExportService {

    private final ReportDataBuilder dataBuilder;
    private final ExcelWriter writer;
    private final OutputDirectoryResolver directoryResolver;
    private final FileNameGenerator fileNameGenerator;

    public ReportExportService(
            ReportDataBuilder dataBuilder,
            ExcelWriter writer,
            OutputDirectoryResolver directoryResolver,
            FileNameGenerator fileNameGenerator
    ) {
        this.dataBuilder = dataBuilder;
        this.writer = writer;
        this.directoryResolver = directoryResolver;
        this.fileNameGenerator = fileNameGenerator;
    }

    public void exportReport(
            List<Team> teams,
            List<PlayerSeasonView> playersData,
            List<Transfer> transfers,
            int event,
            String[] args) {

        File outputFile = resolveOutputFile(event, teams.size(), args);
        ReportData reportData = dataBuilder.build(teams, playersData, transfers);

        writer.export(
                outputFile,
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

    private File resolveOutputFile(
            int event,
            int teamsCount,
            String[] args
    ) {
        File outputDir = directoryResolver.resolve(args);
        String fileName = fileNameGenerator.generate(
                "FPL Report GW-%d (top %d)".formatted(event, teamsCount)
        );
        return new File(outputDir, fileName);
    }

}
