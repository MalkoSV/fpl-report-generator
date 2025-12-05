package fpl.excel.sheets;

import fpl.excel.builder.GenericSheetWriter;
import fpl.excel.style.ExcelStyleFactory;
import fpl.domain.summary.SummaryData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SummarySheetWriter extends GenericSheetWriter<SummaryData> {

    public SummarySheetWriter(SummaryData data) {
        super("Summary", data);
    }

    @Override
    public Sheet writeSheet(Workbook wb, ExcelStyleFactory styles) {

        Sheet sheet = wb.createSheet(sheetName);

        SummaryTableWriter writer = new SummaryTableWriter(sheet, styles);

        int baseCol = 0;

        // -----------------------------
        // 1) Base summary table
        // -----------------------------
        Object[][] baseInfo = {
                {"Teams",          data.teamCount()},
                {"Players",        data.playerCount()},
                {"Triple Captain", data.tripleCaptainCount()},
                {"Wildcard",       data.wildcardCount()},
                {"Bench Boost",    data.benchBoostCount()},
                {"Free Hit",       data.freeHitCount()}
        };

        writer.writeSimpleTable(0, baseCol, baseCol + 1, baseInfo);

        // -----------------------------
        // 2) Zero-point players
        // -----------------------------
        writer.writeMapTable(
                0,
                baseCol + 3, "0 pts players",
                baseCol + 4, "Teams",
                data.zeroPointPlayers()
        );

        // -----------------------------
        // 3) Transfers count
        // -----------------------------
        writer.writeMapTable(
                0,
                baseCol + 6, "Transfers",
                baseCol + 7, "Teams",
                data.transfersCount()
        );

        // -----------------------------
        // 4) Transfers Cost (-4 / -8 etc)
        // -----------------------------
        writer.writeMapTable(
                data.transfersCount().size() + 2,
                baseCol + 6, "Transfers Cost",
                baseCol + 7, "Teams",
                data.transfersCost()
        );

        // -----------------------------
        // 5) Formations
        // -----------------------------
        writer.writeMapTable(
                0,
                baseCol + 9, "Formation",
                baseCol + 10, "Teams",
                data.formations()
        );

        // auto-size
        for (int c = 0; c < 12; c++) sheet.autoSizeColumn(c);

        return sheet;
    }
}
