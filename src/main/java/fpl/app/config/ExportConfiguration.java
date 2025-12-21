package fpl.app.config;

import fpl.excel.core.ExcelWriter;
import fpl.excel.io.WorkbookFactory;

public final class ExportConfiguration {

    private ExportConfiguration() {}

    public static ExcelWriter excelWriter() {
        return new ExcelWriter(
                new WorkbookFactory()
        );
    }
}
