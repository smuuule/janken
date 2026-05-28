package dev.smule.janken.repository;

import java.util.Optional;
import java.util.function.UnaryOperator;

import dev.smule.janken.domain.Game;

public interface GameRepository {

  Game save(Game game);

  Optional<Game> findById(String id);

  Game update(String id, UnaryOperator<Game> updater);
}
