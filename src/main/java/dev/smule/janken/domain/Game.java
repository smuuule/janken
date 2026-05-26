package dev.smule.janken.domain;

import java.util.Objects;

public final class Game {

  private final String id;
  private final Player player1;
  private final Player player2;
  private final GameStatus status;
  private final Player nextPlayerToMove;
  private final Move player1Move;
  private final Move player2Move;
  private final Player winner;

  private Game(String id, Player player1, Player player2, GameStatus status,
      Player nextPlayerToMove, Move player1Move, Move player2Move, Player winner) {
    this.id = requireNonNullOrBlank(id, "id");
    this.player1 = Objects.requireNonNull(player1, "player1 must not be null");
    this.player2 = player2;
    this.status = Objects.requireNonNull(status, "status must not be null");
    this.nextPlayerToMove = nextPlayerToMove;
    this.player1Move = player1Move;
    this.player2Move = player2Move;
    this.winner = winner;
  }

  public static Game create(String id, Player player1) {
    return new Game(id, player1, null, GameStatus.WAITING_FOR_OPPONENT, null, null, null, null);
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

  public Player nextPlayerToMove() {
    return nextPlayerToMove;
  }

  public Move player1Move() {
    return player1Move;
  }

  public Move player2Move() {
    return player2Move;
  }

  public Player winner() {
    return winner;
  }

  public Game join(Player player2) {
    if (status != GameStatus.WAITING_FOR_OPPONENT || this.player2 != null) {
      throw new IllegalStateException("game cannot be joined");
    }

    return new Game(id, player1, Objects.requireNonNull(player2, "joining player must not be null"),
        GameStatus.IN_PROGRESS, player1, null, null, null);
  }

  public Game move(Player player, Move move) {
    if (status != GameStatus.IN_PROGRESS) {
      throw new IllegalStateException("can not make moves right now");
    }
    if (winner != null) {
      throw new IllegalStateException("game is already completed");
    }
    if (nextPlayerToMove == null || !nextPlayerToMove.id().equals(player.id())) {
      throw new IllegalStateException("not this player's turn");
    }

    if (player1.id().equals(player.id())) {
      var newPlayer1Move = move;
      if (player2Move == null) {
        return new Game(id, player1, player2, GameStatus.IN_PROGRESS, player2, newPlayer1Move, null, null);
      }
      return complete(newPlayer1Move, player2Move);
    }

    if (player2 == null || !player2.id().equals(player.id())) {
      throw new IllegalStateException("unknown player");
    }

    var newPlayer2Move = move;
    if (player1Move == null) {
      return new Game(id, player1, player2, GameStatus.IN_PROGRESS, player1, null, newPlayer2Move, null);
    }
    return complete(player1Move, newPlayer2Move);
  }

  private Game complete(Move move1, Move move2) {
    var outcome = GameOutcome.resolve(move1, move2);
    var winnerPlayer = outcome.winner() == null ? null : (outcome.winner() == move1 ? player1 : player2);
    return new Game(id, player1, player2, outcome.status(), null, move1, move2, winnerPlayer);
  }

  private static String requireNonNullOrBlank(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return value.trim();
  }
}
