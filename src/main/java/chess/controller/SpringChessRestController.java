package chess.controller;

import chess.dto.*;
import chess.exception.GameIsNotStartException;
import chess.exception.IllegalRoomException;
import chess.exception.InvalidMoveException;
import chess.exception.NoSuchCommandException;
import chess.service.SpringChessService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chess/api")
public class SpringChessRestController {
    private final SpringChessService springChessService;

    public SpringChessRestController(SpringChessService springChessService) {
        this.springChessService = springChessService;
    }

    @GetMapping("/room/list")
    public ResponseEntity<List<RoomDto>> loadRoom() {
        return ResponseEntity.ok(springChessService.loadAllRoom());
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<BoardDto> createRoom(@PathVariable("id") String id) {
        return ResponseEntity.ok(springChessService.loadRoom(id));
    }

    @PostMapping("/move")
    public ResponseEntity<BoardDto> move(@RequestBody MoveRequestDto moveRequestDto) {
        return ResponseEntity.ok(springChessService.move(moveRequestDto));
    }

    @PostMapping("/movable")
    public ResponseEntity<List<String>> movablePosition(@RequestBody MovablePositionDto movablePositionDto) {
        return ResponseEntity.ok(springChessService.movablePosition(movablePositionDto));
    }

    @GetMapping("/score/{id}")
    public ResponseEntity<BoardStatusDto> score(@PathVariable("id") String id) {
        return ResponseEntity.ok(springChessService.boardStatusDto(id));
    }

    @ExceptionHandler({InvalidMoveException.class, IllegalRoomException.class, GameIsNotStartException.class, NoSuchCommandException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<ErrorMessageDto> moveFail(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorMessageDto(e.getMessage()));
    }
}
