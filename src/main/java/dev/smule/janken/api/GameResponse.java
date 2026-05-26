package dev.smule.janken.api;

import dev.smule.janken.domain.Game;
import dev.smule.janken.domain.GameStatus;

public record GameResponse(String id, GameStatus status, String player1, String player2, String nextPlayerToMove,
    String winner) {

  public static GameResponse from(Game game) {
    return new GameResponse(
        game.id(),
        game.status(),
        game.player1() == null ? null : game.player1().name(),
        game.player2() == null ? null : game.player2().name(),
        game.nextPlayerToMove() == null ? null : game.nextPlayerToMove().name(),
        game.winner() == null ? null : game.winner().name());
  }
}
