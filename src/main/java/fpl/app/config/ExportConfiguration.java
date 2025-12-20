package fpl.app.config;

import fpl.excel.core.ExcelWriter;
import fpl.excel.io.FileNameGenerator;
import fpl.excel.io.WorkbookFactory;
import fpl.output.OutputDirectoryResolver;

public final class ExportConfiguration {

    private ExportConfiguration() {}

    public static ExcelWriter excelWriter() {
        return new ExcelWriter(
                new WorkbookFactory(),
                new OutputDirectoryResolver(),
                new FileNameGenerator()
        );
    }
}
