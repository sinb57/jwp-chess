package chess.controller.dto;

import chess.domain.GameResult;
import chess.domain.TeamColor;
import java.util.List;
import java.util.Map;

public class RoundStatusDto {

    private Map<String, List<String>> movablePositions;
    private TeamColor currentColor;
    private ScoreDto score;
    private boolean checked;
    private boolean isKingDead;

    public RoundStatusDto(
        Map<String, List<String>> movablePositions, TeamColor currentColor,
        GameResult score, boolean checked, boolean isKingDead) {
        this.movablePositions = movablePositions;
        this.currentColor = currentColor;
        this.score = new ScoreDto(score);
        this.checked = checked;
        this.isKingDead = isKingDead;
    }

    public Map<String, List<String>> getMovablePositions() {
        return movablePositions;
    }

    public TeamColor getCurrentColor() {
        return currentColor;
    }

    public ScoreDto getScore() {
        return score;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isKingDead() {
        return isKingDead;
    }
}
