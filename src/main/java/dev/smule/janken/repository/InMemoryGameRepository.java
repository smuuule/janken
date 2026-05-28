package dev.smule.janken.repository;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Repository;

import dev.smule.janken.domain.Game;

@Repository
public class InMemoryGameRepository implements GameRepository {

  private static final int MAX_GAMES = 1024;

  private final HashMap<String, Game> games = new HashMap<>();

  @Override
  public synchronized Game save(Game game) {
    Objects.requireNonNull(game, "game must not be null");

    if (!games.containsKey(game.id()) && games.size() >= MAX_GAMES) {
      throw new IllegalStateException("game store is full");
    }

    games.put(game.id(), game);
    return game;
  }

  @Override
  public synchronized Optional<Game> findById(String id) {
    return Optional.ofNullable(games.get(id));
  }

  @Override
  public synchronized Game update(String id, UnaryOperator<Game> updater) {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(updater, "updater must not be null");

    var current = games.get(id);
    if (current == null) {
      throw new IllegalArgumentException("game not found");
    }

    var updated = Objects.requireNonNull(updater.apply(current), "updated game must not be null");
    if (!id.equals(updated.id())) {
      throw new IllegalStateException("updated game id mismatch");
    }

    games.put(id, updated);
    return updated;
  }
}
