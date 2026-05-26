package dev.smule.janken.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.smule.janken.domain.Game;
import dev.smule.janken.domain.Player;

@RestController
@RequestMapping("/api/games")
public class GameController {

  private final Map<String, Game> games = new HashMap<>();

  @PostMapping
  public ResponseEntity<GameResponse> createGame(@RequestBody CreateGameRequest request) {
    var id = UUID.randomUUID().toString();
    var game = Game.create(id, new Player(UUID.randomUUID().toString(), request.name()));

    games.put(id, game);

    return ResponseEntity.status(HttpStatus.CREATED).body(GameResponse.from(game));
  }

  @PostMapping("/{id}/join")
  public ResponseEntity<GameResponse> joinGame(@PathVariable String id, @RequestBody JoinGameRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{id}/move")
  public ResponseEntity<GameResponse> makeMove(@PathVariable String id, @RequestBody MoveRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<GameResponse> getGame(@PathVariable String id) {
    var game = games.get(id);
    if (game == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(GameResponse.from(game));
  }
}
