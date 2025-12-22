package fpl.excel.core;

import fpl.excel.style.ExcelStyleFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface SheetWriter {
    Sheet writeSheet(Workbook workbook, ExcelStyleFactory styles);
}
