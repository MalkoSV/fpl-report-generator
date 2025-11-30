package fpl.api.model;

public enum PositionType {
    GK(1), DEF(2), MID(3), FWD(4);

    private final int code;

    PositionType(int code) {
        this.code = code;
    }

    public static PositionType fromCode(int code) {
        return switch (code) {
            case 1 -> GK;
            case 2 -> DEF;
            case 3 -> MID;
            case 4 -> FWD;
            default -> throw new IllegalArgumentException("Unknown code: " + code);
        };
    }
}
