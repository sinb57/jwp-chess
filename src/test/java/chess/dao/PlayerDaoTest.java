package chess.dao;

import static chess.domain.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import chess.dao.dto.PlayerDto;

@SpringBootTest
@Transactional
class PlayerDaoTest {

    @Autowired
    private PlayerDao playerDao;

    @DisplayName("데이터 저장 및 조회가 가능해야 한다.")
    @Test
    void saveAndFind() {
        final String colorName = WHITE.getName();
        final String pieces = "a1:Rook,a2:Pawn";
        final Long id = playerDao.save(new PlayerDto(0L, colorName, pieces));

        final PlayerDto playerDto = playerDao.findById(id);
        assertAll(() -> {
                    assertThat(playerDto.getColorName()).isEqualTo(colorName);
                    assertThat(playerDto.getPieces()).isEqualTo(pieces);
                });
    }

    @DisplayName("데이터를 수정할 수 있어야 한다.")
    @Test
    void update() {
        final Long id = playerDao.save(new PlayerDto(0L, WHITE.getName(), "a1:Rook,a2:Pawn"));
        final String expectedPieces = "a3:Rook,a4:Pawn";
        final PlayerDto playerDto = new PlayerDto(id, WHITE.getName(), expectedPieces);
        playerDao.update(playerDto);

        final PlayerDto updatedPlayerDto = playerDao.findById(id);
        assertThat(updatedPlayerDto.getPieces()).isEqualTo(expectedPieces);
    }
}