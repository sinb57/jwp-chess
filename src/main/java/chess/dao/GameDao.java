package chess.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import chess.dao.dto.GameDto;
import chess.dao.dto.GameFinishedDto;
import chess.dao.dto.GameUpdateDto;

@Component
public class GameDao {

    private final JdbcTemplate jdbcTemplate;

    public GameDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final GameDto gameDto) {
        final String query = "INSERT INTO Game (player_id1, player_id2, finished, turn_color) VALUES (?,?,?,?)";
        final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setLong(1, gameDto.getPlayer_id1());
            preparedStatement.setLong(2, gameDto.getPlayer_id2());
            preparedStatement.setBoolean(3, gameDto.getFinished());
            preparedStatement.setString(4, gameDto.getCurrentTurnColor());
            return preparedStatement;
        }, generatedKeyHolder);
        return Objects.requireNonNull(generatedKeyHolder.getKey()).longValue();
    }

    public GameDto findById(final Long id) {
        final String query = "SELECT id, player_id1, player_id2, finished, turn_color FROM Game WHERE id=?";
        return jdbcTemplate.queryForObject(query,
                (resultSet, rowNum) -> new GameDto(
                        resultSet.getLong("id"),
                        resultSet.getLong("player_id1"),
                        resultSet.getLong("player_id2"),
                        resultSet.getBoolean("finished"),
                        resultSet.getString("turn_color")
                ), id);
    }

    public List<GameFinishedDto> findIdAndFinished() {
        final String query = "SELECT id, finished FROM Game ORDER BY id DESC";
        return jdbcTemplate.query(query,
                (resultSet, rowNum) -> new GameFinishedDto(
                        resultSet.getLong(1),
                        resultSet.getBoolean(2)
                ));
    }

    public void update(final GameUpdateDto gameUpdateDto) {
        final String query = "UPDATE Game SET finished=?, turn_color=? WHERE id=?";
        jdbcTemplate.update(query,
                gameUpdateDto.getFinished(),
                gameUpdateDto.getCurrentTurnColor(),
                gameUpdateDto.getId()
        );
    }

    public void remove(final long id) {
        final String query = "DELETE FROM Game WHERE id=?";
        jdbcTemplate.update(query, id);
    }
}
