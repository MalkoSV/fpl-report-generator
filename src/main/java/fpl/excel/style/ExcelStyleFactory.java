package fpl.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

public class ExcelStyleFactory {

    private final Workbook workbook;
    private final Map<StyleKey, CellStyle> cache = new HashMap<>();

    private sealed interface StyleKey
            permits FixedStyle, ColorStyle {
    }

    private enum FixedStyle implements StyleKey {
        HEADER,
        CENTERED,
        SUMMARY_TITLE,
        SUMMARY_VALUE
    }

    private record ColorStyle(Color color) implements StyleKey {
    }

    public ExcelStyleFactory(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle header() {
        return cache.computeIfAbsent(FixedStyle.HEADER, ignored -> {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setAlignment(HorizontalAlignment.CENTER);
            return style;
        });
    }

    public CellStyle centered() {
        return cache.computeIfAbsent(FixedStyle.CENTERED, ignored -> {
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            return style;
        });
    }

    public CellStyle summaryTitle() {
        return cache.computeIfAbsent(FixedStyle.SUMMARY_TITLE, ignored -> {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setAlignment(HorizontalAlignment.LEFT);
            return style;
        });
    }

    public CellStyle summaryValue() {
        return cache.computeIfAbsent(FixedStyle.SUMMARY_VALUE, ignored -> {
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.RIGHT);
            return style;
        });
    }

    public CellStyle withColor(Color color) {
        return cache.computeIfAbsent(new ColorStyle(color), ignored -> {
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(color.getColorIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            return style;
        });
    }

}
