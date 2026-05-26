package dev.smule.janken.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.smule.janken.service.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping
  public ResponseEntity<GameResponse> createGame(@RequestBody CreateGameRequest request) {
    var game = gameService.createGame(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(GameResponse.from(game));
  }

  @PostMapping("/{id}/join")
  public ResponseEntity<GameResponse> joinGame(@PathVariable String id, @RequestBody JoinGameRequest request) {
    return ResponseEntity.ok(GameResponse.from(gameService.joinGame(id, request)));
  }

  @PostMapping("/{id}/move")
  public ResponseEntity<GameResponse> makeMove(@PathVariable String id, @RequestBody MoveRequest request) {
    return ResponseEntity.ok(GameResponse.from(gameService.makeMove(id, request)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GameResponse> getGame(@PathVariable String id) {
    var game = gameService.getGame(id);
    if (game == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(GameResponse.from(game));
  }
}
