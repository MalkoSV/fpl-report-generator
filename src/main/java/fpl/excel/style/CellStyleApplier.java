package fpl.excel.style;

import fpl.domain.model.HasPosition;
import org.apache.poi.ss.usermodel.Cell;

public class CellStyleApplier {

    private final ExcelStyleFactory styles;

    public CellStyleApplier(ExcelStyleFactory styles) {
        this.styles = styles;
    }

    public void header(Cell cell) {
        cell.setCellStyle(styles.header());
    }

    public void centered(Cell cell) {
        cell.setCellStyle(styles.centered());
    }

    public void summaryTitle(Cell cell) {
        cell.setCellStyle(styles.summaryTitle());
    }

    public void summaryValue(Cell cell) {
        cell.setCellStyle(styles.summaryValue());
    }

    public void playerColor(Cell cell, HasPosition player) {
        cell.setCellStyle(styles.withColor(ColorUtils.getColorForCell(player)));
    }
}
