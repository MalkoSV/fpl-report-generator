package fpl.output;

import fpl.domain.model.PlayerSeasonView;
import fpl.domain.transfers.Transfer;
import fpl.domain.stats.SummaryData;
import fpl.domain.transfers.TransfersData;
import fpl.domain.transfers.TransfersDataBuilder;
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
import fpl.domain.filters.PlayerSeasonStatsFilter;
import fpl.domain.model.Team;
import fpl.domain.stats.TeamSummary;
import fpl.domain.filters.PlayerGameweekStatsFilter;
import fpl.domain.stats.TeamStats;
import fpl.excel.sheets.TransfersSheetWriter;

import java.util.List;

public class ReportExportService {

    public void exportResults(
            List<Team> teams,
            List<PlayerSeasonView> playersData,
            List<Transfer> transfers,
            int event,
            String[] args) {

        ExcelWriter writer = new ExcelWriter(
                new WorkbookFactory(),
                new OutputDirectoryResolver(),
                new FileNameGenerator()
        );

        TeamSummary summary = TeamStats.calculateSummary(teams);
        SummaryData summaryData = SummaryData.from(teams, summary);
        TransfersData transfersData = new TransfersDataBuilder().build(transfers);

        writer.writeExcel(
                "FPL Report GW-%d (top %d)".formatted(event, teams.size()),
                args,
                new GameweekPlayersSheetWriter(summary.players()),
                new CaptainPlayersSheetWriter(PlayerGameweekStatsFilter.captained(summary.players())),
                new StartPlayersSheetWriter(PlayerGameweekStatsFilter.startersOnly(summary.players())),
                new BenchPlayersSheetWriter(PlayerGameweekStatsFilter.benchOnly(summary.players())),
                new DoubtfulPlayersSheetWriter(PlayerGameweekStatsFilter.doubtful(summary.players())),
                new HighPointsBenchSheetWriter(PlayerGameweekStatsFilter.highPointsBench(summary.players())),
                new SummarySheetWriter(summaryData),
                new TransfersSheetWriter(transfersData),
                new PlayerStatsSheetWriter(PlayerSeasonStatsFilter.filterTopPlayers(playersData, 25, 2.75,0.1))
                );
    }
}
