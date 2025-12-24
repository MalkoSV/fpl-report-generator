package fpl.excel.core;

import fpl.excel.io.WorkbookFactory;
import fpl.excel.style.ExcelStyleFactory;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ExcelWriter {

    private static final Logger logger = Logger.getLogger(ExcelWriter.class.getName());

    private final WorkbookFactory workbookFactory;

    public ExcelWriter(WorkbookFactory workbookFactory) {
        this.workbookFactory = workbookFactory;
    }

    public void export(File outputFile,
                       List<SheetWriter> sheetWriters) {

        try (Workbook workbook = workbookFactory.createWorkbook()) {

            ExcelStyleFactory styleFactory = new ExcelStyleFactory(workbook);

            for (var writer : sheetWriters) {
                writer.writeSheet(workbook, styleFactory);
            }

            try (FileOutputStream out = new FileOutputStream(outputFile)) {
                workbook.write(out);
            }

            logger.info("Excel file saved: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            throw new ExcelExportException("Failed to write Excel file: " + outputFile.getAbsolutePath(), e);
        }
    }
}
