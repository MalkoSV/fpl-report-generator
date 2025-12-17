package fpl.excel.style;

import org.apache.poi.ss.usermodel.IndexedColors;

public enum Color {
    LIGHT_YELLOW(IndexedColors.LIGHT_YELLOW.getIndex()),
    LIGHT_ORANGE(IndexedColors.LIGHT_ORANGE.getIndex()),
    TURQUOISE(IndexedColors.TURQUOISE.getIndex()),
    CORNFLOWER_BLUE(IndexedColors.CORNFLOWER_BLUE.getIndex());

    private final short colorIndex;

    Color(short colorIndex) {
        this.colorIndex = colorIndex;
    }

    public short getColorIndex() {
        return colorIndex;
    }

}
