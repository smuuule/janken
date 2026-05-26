package dev.smule.janken.domain;

import java.util.Objects;

public final class Game {

  private final String id;
  private final Player player1;
  private final Player player2;
  private final GameStatus status;

  private Game(String id, Player player1, Player player2, GameStatus status) {
    this.id = requireNonNullOrBlank(id, "id");
    this.player1 = Objects.requireNonNull(player1, "player1 must not be null");
    this.player2 = player2;
    this.status = Objects.requireNonNull(status, "status must not be null");
  }

  public static Game create(String id, Player player1) {
    return new Game(id, player1, null, GameStatus.WAITING_FOR_OPPONENT);
  }

  public String id() {
    return id;
  }

  public Player player1() {
    return player1;
  }

  public Player player2() {
    return player2;
  }

  public GameStatus status() {
    return status;
  }

  private static String requireNonNullOrBlank(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return value.trim();
  }
}
