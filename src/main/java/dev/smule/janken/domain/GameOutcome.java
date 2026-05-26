package dev.smule.janken.domain;

import java.util.Objects;

public record GameOutcome(GameStatus status, Move winner) {

  public GameOutcome {
    status = Objects.requireNonNull(status, "status must not be null");
  }

  public static GameOutcome resolve(Move move1, Move move2) {
    Objects.requireNonNull(move1, "firstMove must not be null");
    Objects.requireNonNull(move2, "secondMove must not be null");

    if (move1 == move2) {
      return new GameOutcome(GameStatus.COMPLETED, null);
    }

    return new GameOutcome(GameStatus.COMPLETED, move1.beats(move2) ? move1 : move2);
  }

  public boolean isTie() {
    return winner == null;
  }
}
