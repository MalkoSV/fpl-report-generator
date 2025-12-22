package fpl.excel.sheets;

import fpl.excel.style.CellStyler;
import fpl.excel.style.ExcelStyleFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public class SummaryTableWriter {

    private final Sheet sheet;
    private final ExcelStyleFactory styles;

    public SummaryTableWriter(Sheet sheet, ExcelStyleFactory styles) {
        this.sheet = sheet;
        this.styles = styles;
    }

    public int writeSimpleTable(
            int startRow,
            int col1,
            int col2,
            Object[][] rows
    ) {
        CellStyler styler = new CellStyler(styles);
        int currentRow = startRow;

        for (Object[] r : rows) {
            Row row = getOrCreateRow(currentRow);

            Cell c1 = row.createCell(col1);
            setCellValue(c1, r[0]);
            styler.applySummaryTitle(c1);

            Cell c2 = row.createCell(col2);
            setCellValue(c2, r[1]);
            c2.setCellStyle(styles.summaryValue());
            styler.applySummaryValue(c2);

            currentRow++;
        }
        return currentRow;
    }

    public int writeMapTable(
            int startRow,
            int col1, String title1,
            int col2, String title2,
            Map<?, ?> map
    ) {
        CellStyler styler = new CellStyler(styles);
        Row headerRow = getOrCreateRow(startRow);

        Cell h1 = headerRow.createCell(col1);
        h1.setCellValue(title1);
        styler.applyHeader(h1);

        Cell h2 = headerRow.createCell(col2);
        h2.setCellValue(title2);
        styler.applyHeader(h2);

        int currentRow = startRow + 1;
        for (var entry : map.entrySet()) {

            Row r = getOrCreateRow(currentRow);

            Cell c1 = r.createCell(col1);
            setCellValue(c1, entry.getKey());
            styler.applyCentered(c1);

            Cell c2 = r.createCell(col2);
            styler.applyCentered(c2);

            currentRow++;
        }

        return currentRow;
    }

    private Row getOrCreateRow(int rowNum) {
        Row row = sheet.getRow(rowNum);
        return row != null ? row : sheet.createRow(rowNum);
    }

    private void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setBlank();
            return;
        }

        if (value instanceof Number num) {
            cell.setCellValue(num.doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
