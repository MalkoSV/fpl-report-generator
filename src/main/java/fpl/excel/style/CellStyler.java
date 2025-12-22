package fpl.excel.style;

import fpl.domain.model.HasPosition;
import org.apache.poi.ss.usermodel.Cell;

public class CellStyler {

    private final ExcelStyleFactory styles;

    public CellStyler(ExcelStyleFactory styles) {
        this.styles = styles;
    }

    public void applyHeader(Cell cell) {
        cell.setCellStyle(styles.header());
    }

    public void applyCentered(Cell cell) {
        cell.setCellStyle(styles.centered());
    }

    public void applySummaryTitle(Cell cell) {
        cell.setCellStyle(styles.summaryTitle());
    }

    public void applySummaryValue(Cell cell) {
        cell.setCellStyle(styles.summaryValue());
    }

    public void applyPlayerColor(Cell cell, HasPosition player) {
        cell.setCellStyle(styles.withColor(ColorUtils.getColorForCell(player)));
    }
}
