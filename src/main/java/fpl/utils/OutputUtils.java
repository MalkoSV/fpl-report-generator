package fpl.utils;

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
import fpl.output.OutputDirectoryResolver;
import fpl.domain.stats.PlayerFilterService;
import fpl.api.dto.PlayerDto;
import fpl.domain.model.Team;
import fpl.domain.model.TeamSummary;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class OutputUtils {

    private static final Logger logger = Logger.getLogger(OutputUtils.class.getName());

    public static final List<String> COLUMNHEADERS = List.of(
            "Name",
            "Count",
            "Start",
            "Captain",
            "Triple",
            "Vice",
            "Bench",
            "Availability",
            "Points"
    );

    public static void exportResultsToExcel(List<Team> teams, List<PlayerDto> playersData, String fileName, String[] args) {

        ExcelWriter writer = new ExcelWriter(
                new WorkbookFactory(),
                new OutputDirectoryResolver(),
                new FileNameGenerator()
        );

        TeamSummary summary = TeamUtils.calculateSummary(teams);
        writer.writeExcel(
                "FPL Report GW-14 (top %d)_".formatted(teams.size()),
                args,
                new GameweekPlayersSheetWriter(summary.players()),
                new CaptainPlayersSheetWriter(PlayerUtils.getPlayersWhoCaptain(summary.players())),
                new StartPlayersSheetWriter(PlayerUtils.getOnlyStartPlayers(summary.players())),
                new BenchPlayersSheetWriter(PlayerUtils.getOnlyBenchPlayers(summary.players())),
                new DoubtfulPlayersSheetWriter(PlayerUtils.getDoubtfulPlayers(summary.players())),
                new HighPointsBenchSheetWriter(PlayerUtils.getBenchPlayersWithHighPoints(summary.players())),
//                new SummarySheetWriter(PlayerUtils.getBenchPlayersWithHighPoints(summary.players())),
                new PlayerStatsSheetWriter(PlayerFilterService.filter(playersData, 20, 3,1.2))
                );

//            addSummaryInformation(workbook, allPlayersSheet, teams, summary);
    }

    public static void addSummaryInformation(
            Workbook workbook,
            Sheet sheetName,
            List<Team> teams,
            TeamSummary summary
    ) {

        Map<String, Long> formations = TeamUtils.calculateFormationType(teams);
        Map<Long, Long> zeroPlayersCount = TeamUtils.calculateStartPlayersWithZero(teams);
        Map<Integer, Long> transfersCount = TeamUtils.calculateTransfers(teams);
        Map<Integer, Long> transfersCostCount = TeamUtils.calculateTransfersCost(teams);

        int baseColumn = COLUMNHEADERS.size();
        int summaryColumn1 = baseColumn + 1;
        int summaryColumn2 = baseColumn + 2;
        int zeroPlayersColumn1 = summaryColumn2 + 2;
        int zeroPlayersColumn2 = summaryColumn2 + 3;
        int transfersColumn1 = zeroPlayersColumn2 + 2;
        int transfersColumn2 = zeroPlayersColumn2 + 3;
        int formationsColumn1 = transfersColumn2 + 2;
        int formationsColumn2 = transfersColumn2 + 3;

        Object[][] teamsInformation = {
                {"Teams",          summary.count()},
                {"Players",        summary.players().size()},
                {"Triple Captain", summary.tripleCaptain()},
                {"Wildcard",       summary.wildcard()},
                {"Bench Boost",    summary.benchBoost()},
                {"Free Hit",       summary.freeHit()}
        };
        CellStyle headerStyle = getHeaderStyle(workbook);
        headerStyle.setAlignment(HorizontalAlignment.LEFT);

        writeSimpleTable(sheetName, 0,
                summaryColumn1,
                summaryColumn2,
                headerStyle,
                teamsInformation
        );
        writeMappedSection(sheetName, 0,
                zeroPlayersColumn1, "0 pts players",
                zeroPlayersColumn2, "Teams",
                headerStyle,
                zeroPlayersCount
        );
        writeMappedSection(sheetName, 0,
                transfersColumn1, "Total transfers",
                transfersColumn2, "Teams",
                headerStyle,
                transfersCount
        );
        writeMappedSection(sheetName, transfersCount.size() + 1,
                transfersColumn1, "Transfers cost",
                transfersColumn2, "Teams",
                headerStyle,
                transfersCostCount
        );
        writeMappedSection(sheetName, 0,
                formationsColumn1, "Formations",
                formationsColumn2, "Teams",
                headerStyle,
                formations
        );

        sheetName.autoSizeColumn(summaryColumn1);
        sheetName.autoSizeColumn(summaryColumn2);
        sheetName.autoSizeColumn(zeroPlayersColumn1);
        sheetName.autoSizeColumn(zeroPlayersColumn2);
        sheetName.autoSizeColumn(transfersColumn1);
        sheetName.autoSizeColumn(transfersColumn2);
        sheetName.autoSizeColumn(formationsColumn1);
        sheetName.autoSizeColumn(formationsColumn2);
    }

    public static CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        return headerStyle;
    }

    private static int writeMappedSection(
            Sheet sheet,
            int startRow,
            int col1,
            String title1,
            int col2,
            String title2,
            CellStyle style,
            Map<?,?> map
    ) {
        Row header = sheet.getRow(startRow);
        if (header == null) {
            header = sheet.createRow(startRow);
        }

        Cell h1 = header.createCell(col1);
        h1.setCellValue(title1);
        h1.setCellStyle(style);

        Cell h2 = header.createCell(col2);
        h2.setCellValue(title2);
        h2.setCellStyle(style);

        return writeMapBlock(sheet, startRow + 1, col1, col2, map);
    }

    private static int writeSimpleTable(
            Sheet sheet,
            int startRow,
            int col1,
            int col2,
            CellStyle style,
            Object[][] rows
    ) {
        int rowNum = startRow;

        for (Object[] rowData : rows) {
            Row row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);

            Cell c1 = row.createCell(col1);
            c1.setCellValue((String) rowData[0]);
            c1.setCellStyle(style);

            row.createCell(col2).setCellValue(((Number) rowData[1]).doubleValue());

            rowNum++;
        }
        return rowNum;
    }

    private static int writeMapBlock(
            Sheet sheet,
            int startRow,
            int col1,
            int col2,
            Map<?, ?> map
    ) {

        int rowNum = startRow;
        CellStyle simpleStyle = sheet.getWorkbook().createCellStyle();
        simpleStyle.setAlignment(HorizontalAlignment.CENTER);

        for (var entry : map.entrySet()) {
            Row row = sheet.getRow(rowNum);

            if (row == null){
                row = sheet.createRow(rowNum);
            }

            Cell c1 = row.createCell(col1);
            Object key = entry.getKey();
            if (key instanceof Number n) {
                c1.setCellValue(n.doubleValue());
            } else {
                c1.setCellValue(key.toString());
            }
            c1.setCellStyle(simpleStyle);

            Cell c2 = row.createCell(col2);
            Object value = entry.getValue();
            if (value instanceof Number n) {
                c2.setCellValue(n.doubleValue());
            } else {
                c2.setCellValue(value.toString());
            }
            rowNum++;
        }
        return rowNum;
    }

}
