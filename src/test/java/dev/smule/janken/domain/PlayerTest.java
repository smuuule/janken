package dev.smule.janken.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PlayerTest {

  @Test
  void trimsPlayerInfo() {
    var player = new Player("  42  ", "  Stefan  ");

    assertThat(player.id()).isEqualTo("42");
    assertThat(player.name()).isEqualTo("Stefan");
  }

  @Test
  void rejectsNoPlayerId() {
    assertThatThrownBy(() -> new Player("", "Alice"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
