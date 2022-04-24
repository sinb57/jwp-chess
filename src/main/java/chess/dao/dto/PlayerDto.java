package chess.dao.dto;

public class PlayerDto {

    private final Long id;
    private final String colorName;
    private final String pieces;

    public PlayerDto(final Long id, final String colorName, final String pieces) {
        this.id = id;
        this.colorName = colorName;
        this.pieces = pieces;
    }

    public Long getId() {
        return id;
    }

    public String getColorName() {
        return colorName;
    }

    public String getPieces() {
        return pieces;
    }
}
