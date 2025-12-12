package fpl.excel.utils;

import fpl.excel.builder.Col;

import java.util.List;

public class FormatUtils {

    private FormatUtils() {}

    public static <T> int calculateColumnWidth(List<Col<T>> columns) {
        int maxHeaderLength = columns.stream()
                .mapToInt(c -> c.title().length())
                .max()
                .orElse(10);

        return  maxHeaderLength * 256;
    }
}
