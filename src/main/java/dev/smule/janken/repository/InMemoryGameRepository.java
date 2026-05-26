package dev.smule.janken.repository;

import dev.smule.janken.domain.Game;
import java.util.Optional;
import java.util.HashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryGameRepository implements GameRepository {

  private final HashMap<String, Game> games = new HashMap<>();

  @Override
  public Game save(Game game) {
    games.put(game.id(), game);
    return game;
  }

  @Override
  public Optional<Game> findById(String id) {
    return Optional.ofNullable(games.get(id));
  }
}
