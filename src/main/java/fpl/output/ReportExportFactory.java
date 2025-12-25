package fpl.output;

import fpl.app.config.ExportConfiguration;
import fpl.domain.transfers.TransfersDataBuilder;
import fpl.excel.io.FileNameGenerator;
import fpl.output.builder.DefaultTopPlayersSelectionPolicy;
import fpl.output.builder.ReportDataBuilder;
import fpl.output.layout.ReportLayout;

public final class ReportExportFactory {

    private ReportExportFactory() {}

    public static ReportExportService createExcel() {
        return  new ReportExportService(
                new ReportDataBuilder(
                        new TransfersDataBuilder(),
                        new DefaultTopPlayersSelectionPolicy()
                ),
                ExportConfiguration.excelWriter(),
                new ReportLayout(),
                new FileNameGenerator());
    }
}
