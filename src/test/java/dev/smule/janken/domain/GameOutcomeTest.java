package dev.smule.janken.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GameOutcomeTest {

  @Test
  void rockBeatsScissors() {
    var outcome = GameOutcome.resolve(Move.ROCK, Move.SCISSORS);

    assertThat(outcome.status()).isEqualTo(GameStatus.COMPLETED);
    assertThat(outcome.winner()).isEqualTo(Move.ROCK);
    assertThat(outcome.isTie()).isFalse();
  }

  @Test
  void scissorsBeatsPaper() {
    var outcome = GameOutcome.resolve(Move.SCISSORS, Move.PAPER);

    assertThat(outcome.status()).isEqualTo(GameStatus.COMPLETED);
    assertThat(outcome.winner()).isEqualTo(Move.SCISSORS);
    assertThat(outcome.isTie()).isFalse();
  }

  @Test
  void paperBeatsRock() {
    var outcome = GameOutcome.resolve(Move.PAPER, Move.ROCK);

    assertThat(outcome.status()).isEqualTo(GameStatus.COMPLETED);
    assertThat(outcome.winner()).isEqualTo(Move.PAPER);
    assertThat(outcome.isTie()).isFalse();
  }

  @Test
  void tieIsCompletedGame() {
    var outcome = GameOutcome.resolve(Move.ROCK, Move.ROCK);

    assertThat(outcome.status()).isEqualTo(GameStatus.COMPLETED);
    assertThat(outcome.winner()).isNull();
    assertThat(outcome.isTie()).isTrue();
  }
}
