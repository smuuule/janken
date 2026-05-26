package dev.smule.janken.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MoveTest {

  @Test
  void checkMoves() {
    assertThat(Move.values()).containsExactly(Move.ROCK, Move.PAPER, Move.SCISSORS);
  }

  @Test
  void rockBeatsScissors() {
    assertThat(Move.ROCK.beats(Move.SCISSORS)).isTrue();
    assertThat(Move.SCISSORS.beats(Move.ROCK)).isFalse();
  }

  @Test
  void paperBeatsRock() {
    assertThat(Move.PAPER.beats(Move.ROCK)).isTrue();
    assertThat(Move.ROCK.beats(Move.PAPER)).isFalse();
  }

  @Test
  void scissorsBeatsPaper() {
    assertThat(Move.SCISSORS.beats(Move.PAPER)).isTrue();
    assertThat(Move.PAPER.beats(Move.SCISSORS)).isFalse();
  }

  @Test
  void tieDoesNotCountAsBeating() {
    assertThat(Move.ROCK.beats(Move.ROCK)).isFalse();
    assertThat(Move.PAPER.beats(Move.PAPER)).isFalse();
    assertThat(Move.SCISSORS.beats(Move.SCISSORS)).isFalse();
  }

  @Test
  void rejectInvalidMove() {
    assertThatThrownBy(() -> Move.ROCK.beats(null))
        .isInstanceOf(NullPointerException.class);
  }
}
