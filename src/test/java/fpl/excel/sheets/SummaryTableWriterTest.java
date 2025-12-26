package fpl.excel.sheets;

import fpl.excel.style.ExcelStyleFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SummaryTableWriterTest {

    @Test
    public void writes_map_values_into_cells() {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("test");

        ExcelStyleFactory styles = new ExcelStyleFactory(wb);
        SummaryTableWriter writer = new SummaryTableWriter(sheet, styles);

        Map<Integer, Integer> data = Map.of(0, 10);

        writer.writeMapTable(0, 0, "Key", 1, "Value", data);

        Row row = sheet.getRow(1);
        Cell c0 = row.getCell(0);
        Cell c1 = row.getCell(1);

        assertThat(c0.getNumericCellValue()).isEqualTo(0);
        assertThat(c1.getNumericCellValue()).isEqualTo(10);

        CellStyle cellStyle = c1.getCellStyle();
        assertEquals(HorizontalAlignment.CENTER, cellStyle.getAlignment());

        Cell header = sheet.getRow(0).getCell(0);
        CellStyle headerCellStyle = header.getCellStyle();
        assertEquals(HorizontalAlignment.CENTER, headerCellStyle.getAlignment());

        Font font = wb.getFontAt(headerCellStyle.getFontIndex());
        assertTrue(font.getBold());
    }
}
