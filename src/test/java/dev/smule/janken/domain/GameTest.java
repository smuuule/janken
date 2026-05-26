package dev.smule.janken.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void createsDefaultGame() {
    var player1 = new Player("42", "Stefan");

    var game = Game.create("game404", player1);

    assertThat(game.id()).isEqualTo("game404");
    assertThat(game.player1()).isEqualTo(player1);
    assertThat(game.player2()).isNull();
    assertThat(game.status()).isEqualTo(GameStatus.WAITING_FOR_OPPONENT);
  }

  @Test
  void rejectsNoGameId() {
    var player1 = new Player("42", "Stefan");

    assertThatThrownBy(() -> Game.create("  ", player1))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
