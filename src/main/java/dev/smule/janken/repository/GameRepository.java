package dev.smule.janken.repository;

import java.util.Optional;

import dev.smule.janken.domain.Game;

public interface GameRepository {

  Game save(Game game);

  Optional<Game> findById(String id);
}
