package fpl.excel.builder;

import fpl.domain.model.HasPosition;
import fpl.excel.style.CellStyleApplier;
import fpl.excel.style.ExcelStyleFactory;
import fpl.excel.utils.FormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class TableSheetWriter<T> extends GenericSheetWriter<List<T>> {

    private final List<Col<T>> columns;

    public TableSheetWriter(String sheetName, List<T> data, List<Col<T>> columns) {
        super(sheetName, data);
        this.columns = columns;
    }

    @Override
    public Sheet writeSheet(Workbook wb, ExcelStyleFactory styles) {

        Sheet sheet = wb.createSheet(sheetName);
        CellStyleApplier styler = new CellStyleApplier(styles);

        Row header = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns.get(i).title());
            styler.header(cell);
        }

        int rowNum = 1;
        for (T item : data) {
            Row row = sheet.createRow(rowNum++);

            for (int col = 0; col < columns.size(); col++) {
                Object value = columns.get(col).extractor().apply(item);
                Cell cell = row.createCell(col);

                if (value instanceof Number n) {
                    cell.setCellValue(n.doubleValue());
                } else if (value != null) {
                    cell.setCellValue(value.toString());
                } else {
                    cell.setBlank();
                }

                if (item instanceof HasPosition hp && col == 0) {
                    styler.playerColor(cell, hp);
                }
            }
        }
        sheet.autoSizeColumn(0);

        int width = FormatUtils.calculateColumnWidth(columns);
        for (int i = 1; i < columns.size(); i++) {
            sheet.setColumnWidth(i, width);
        }

        return sheet;
    }
}
