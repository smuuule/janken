package dev.smule.janken.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GameStatusTest {

  @Test
  void checkStatuses() {
    assertThat(GameStatus.values()).containsExactly(
        GameStatus.WAITING_FOR_OPPONENT,
        GameStatus.IN_PROGRESS,
        GameStatus.COMPLETED);
  }
}
