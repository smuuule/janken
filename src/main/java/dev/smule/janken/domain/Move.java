package dev.smule.janken.domain;

import java.util.Objects;

public enum Move {
  ROCK,
  PAPER,
  SCISSORS;

  public boolean beats(Move other) {
    Objects.requireNonNull(other, "other must not be null");

    return switch (this) {
      case ROCK -> other == SCISSORS;
      case PAPER -> other == ROCK;
      case SCISSORS -> other == PAPER;
      default -> false;
    };
  }
}
