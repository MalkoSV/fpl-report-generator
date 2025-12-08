package fpl.output;

import fpl.domain.summary.SummaryData;
import fpl.domain.summary.SummaryDataBuilder;
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
import fpl.domain.utils.PlayerFilter;
import fpl.api.dto.PlayerDto;
import fpl.domain.model.Team;
import fpl.domain.model.TeamSummary;
import fpl.domain.utils.PlayerUtils;
import fpl.domain.utils.TeamUtils;

import java.util.List;

public class ReportExportService {

    public void exportResults(
            List<Team> teams,
            List<PlayerDto> playersData,
            String[] args) {

        ExcelWriter writer = new ExcelWriter(
                new WorkbookFactory(),
                new OutputDirectoryResolver(),
                new FileNameGenerator()
        );

        TeamSummary summary = TeamUtils.calculateSummary(teams);
        SummaryData summaryData = new SummaryDataBuilder().build(teams, summary);
        writer.writeExcel(
                "FPL Report GW-14 (top %d)".formatted(teams.size()),
                args,
                new GameweekPlayersSheetWriter(summary.players()),
                new CaptainPlayersSheetWriter(PlayerUtils.getPlayersWhoCaptain(summary.players())),
                new StartPlayersSheetWriter(PlayerUtils.getOnlyStartPlayers(summary.players())),
                new BenchPlayersSheetWriter(PlayerUtils.getOnlyBenchPlayers(summary.players())),
                new DoubtfulPlayersSheetWriter(PlayerUtils.getDoubtfulPlayers(summary.players())),
                new HighPointsBenchSheetWriter(PlayerUtils.getBenchPlayersWithHighPoints(summary.players())),
                new SummarySheetWriter(summaryData),
                new PlayerStatsSheetWriter(PlayerFilter.filter(playersData, 25, 2,1.0))
                );
    }
}
