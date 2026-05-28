package dev.smule.janken.service;

import dev.smule.janken.api.CreateGameRequest;
import dev.smule.janken.api.JoinGameRequest;
import dev.smule.janken.api.MoveRequest;
import dev.smule.janken.domain.Game;
import dev.smule.janken.domain.Player;
import dev.smule.janken.repository.GameRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private final GameRepository gameRepository;

  public GameService(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public Game createGame(CreateGameRequest request) {
    var game = Game.create(UUID.randomUUID().toString(), new Player(UUID.randomUUID().toString(), request.name()));
    return gameRepository.save(game);
  }

  public Game getGame(String id) {
    return gameRepository.findById(id).orElse(null);
  }

  public Game joinGame(String id, JoinGameRequest request) {
    return gameRepository.update(id, game -> {
      if (game.player1().name().equals(request.name())) {
        throw new IllegalStateException("player already joined");
      }

      return game.join(new Player(UUID.randomUUID().toString(), request.name()));
    });
  }

  public Game makeMove(String id, MoveRequest request) {
    return gameRepository.update(id, game -> {
      var player = resolvePlayer(game, request.name());
      return game.move(player, request.move());
    });
  }

  private Player resolvePlayer(Game game, String name) {
    if (game.player1().name().equals(name)) {
      return game.player1();
    }
    if (game.player2() != null && game.player2().name().equals(name)) {
      return game.player2();
    }

    throw new IllegalStateException("unknown player");
  }
}
