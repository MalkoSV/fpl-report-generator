package fpl.output;

import fpl.domain.model.PlayerSeasonView;
import fpl.domain.transfers.Transfer;
import fpl.excel.core.ExcelWriter;
import fpl.excel.io.FileNameGenerator;
import fpl.domain.model.Team;
import fpl.output.builder.ReportDataBuilder;
import fpl.output.excel.SheetWriterFactory;
import fpl.output.model.ReportData;

import java.io.File;
import java.util.List;

public class ReportExportService {

    private final ReportDataBuilder dataBuilder;
    private final ExcelWriter writer;
    private final SheetWriterFactory sheetWriterFactory;
    private final FileNameGenerator fileNameGenerator;

    public ReportExportService(
            ReportDataBuilder dataBuilder,
            ExcelWriter writer,
            SheetWriterFactory sheetWriterFactory,
            FileNameGenerator fileNameGenerator
    ) {
        this.dataBuilder = dataBuilder;
        this.writer = writer;
        this.sheetWriterFactory = sheetWriterFactory;
        this.fileNameGenerator = fileNameGenerator;
    }

    public void exportReport(
            List<Team> teams,
            List<PlayerSeasonView> playersData,
            List<Transfer> transfers,
            int event,
            OutputContext outputContext
    ) {
        File outputFile = resolveOutputFile(
                event,
                teams.size(),
                outputContext.outputDirectory()
        );

        ReportData reportData = dataBuilder.build(
                teams,
                playersData,
                transfers
        );

        writer.export(
                outputFile,
                sheetWriterFactory.create(reportData)
        );
    }

    private File resolveOutputFile(
            int event,
            int teamsCount,
            File outputDir
    ) {
        String fileName = fileNameGenerator.generate("FPL Report GW-%d (top %d)".formatted(event, teamsCount));

        return new File(outputDir, fileName);
    }

}
