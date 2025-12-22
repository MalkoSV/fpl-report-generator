package fpl.excel.builder;

import fpl.excel.core.SheetWriter;
import fpl.excel.style.ExcelStyleFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class GenericSheetWriter<T> implements SheetWriter {

    protected final String sheetName;
    protected final T data;

    protected GenericSheetWriter(String sheetName, T data) {
        this.sheetName = sheetName;
        this.data = data;
    }

    public abstract Sheet writeSheet(Workbook wb, ExcelStyleFactory styles);
}
