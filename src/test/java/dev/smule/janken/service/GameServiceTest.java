package dev.smule.janken.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import dev.smule.janken.api.CreateGameRequest;
import dev.smule.janken.api.JoinGameRequest;
import dev.smule.janken.api.MoveRequest;
import dev.smule.janken.domain.GameStatus;
import dev.smule.janken.domain.Move;
import dev.smule.janken.repository.GameRepository;
import dev.smule.janken.repository.InMemoryGameRepository;

class GameServiceTest {

  private final GameRepository gameRepository = new InMemoryGameRepository();
  private final GameService gameService = new GameService(gameRepository);

  @Test
  void joinAndCompleteGame() {
    var created = gameService.createGame(new CreateGameRequest("Lisa"));

    var joined = gameService.joinGame(created.id(), new JoinGameRequest("Pelle"));
    assertThat(joined.status()).isEqualTo(GameStatus.IN_PROGRESS);

    var firstMove = gameService.makeMove(created.id(), new MoveRequest("Lisa", Move.ROCK));
    assertThat(firstMove.status()).isEqualTo(GameStatus.IN_PROGRESS);

    var completed = gameService.makeMove(created.id(), new MoveRequest("Pelle", Move.SCISSORS));
    assertThat(completed.status()).isEqualTo(GameStatus.COMPLETED);
    assertThat(completed.winner().name()).isEqualTo("Lisa");
  }

  @Test
  void rejectsJoiningTwice() {
    var created = gameService.createGame(new CreateGameRequest("Lisa"));
    gameService.joinGame(created.id(), new JoinGameRequest("Pelle"));

    assertThatThrownBy(() -> gameService.joinGame(created.id(), new JoinGameRequest("Mika")))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void rejectsMovesBeforeOpponentJoins() {
    var created = gameService.createGame(new CreateGameRequest("Lisa"));

    assertThatThrownBy(() -> gameService.makeMove(created.id(), new MoveRequest("Lisa", Move.ROCK)))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void rejectsOutOfOrderMoves() {
    var created = gameService.createGame(new CreateGameRequest("Lisa"));
    gameService.joinGame(created.id(), new JoinGameRequest("Pelle"));

    assertThatThrownBy(() -> gameService.makeMove(created.id(), new MoveRequest("Pelle", Move.SCISSORS)))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void rejectsMovesAfterCompletion() {
    var created = gameService.createGame(new CreateGameRequest("Lisa"));
    gameService.joinGame(created.id(), new JoinGameRequest("Pelle"));
    gameService.makeMove(created.id(), new MoveRequest("Lisa", Move.ROCK));
    gameService.makeMove(created.id(), new MoveRequest("Pelle", Move.SCISSORS));

    assertThatThrownBy(() -> gameService.makeMove(created.id(), new MoveRequest("Lisa", Move.ROCK)))
        .isInstanceOf(IllegalStateException.class);
  }
}
