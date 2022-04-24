package chess.dao;

import static chess.domain.Color.BLACK;
import static chess.domain.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import chess.dao.dto.GameDto;
import chess.dao.dto.GameUpdateDto;

@SpringBootTest
@Transactional
class GameDaoTest {

    @Autowired
    private GameDao gameDao;

    @DisplayName("데이터 저장 및 조회가 가능해야 한다.")
    @Test
    void saveAndFind() {
        final boolean finished = false;
        final String currentTurnColor = WHITE.getName();
        final Long id = gameDao.save(new GameDto(0L, 1L, 2L, finished, currentTurnColor));

        final GameDto gameDto = gameDao.findById(id);
        assertAll(() -> {
            assertThat(gameDto.getFinished()).isEqualTo(finished);
            assertThat(gameDto.getCurrentTurnColor()).isEqualTo(currentTurnColor);
        });
    }

    @DisplayName("데이터를 수정할 수 있어야 한다.")
    @Test
    void update() {
        final Long id = gameDao.save(new GameDto(0L, 1L, 2L, false, WHITE.getName()));

        final boolean finished = true;
        final String currentTurnColor = BLACK.getName();
        final GameUpdateDto gameUpdateDto = new GameUpdateDto(id, finished, currentTurnColor);
        gameDao.update(gameUpdateDto);

        final GameDto updatedGameDto = gameDao.findById(id);
        assertAll(() -> {
            assertThat(updatedGameDto.getFinished()).isEqualTo(finished);
            assertThat(updatedGameDto.getCurrentTurnColor()).isEqualTo(currentTurnColor);
        });
    }
}