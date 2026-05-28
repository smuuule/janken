# Janken

Minimal REST API for games of Rock, Paper, Scissors (or jan-ken-pon) built with the Spring Framework

## Build

```bash
./mvnw clean test
./mvnw clean package
```

## Run

```bash
./mvnw spring-boot:run
```

This will start Spring and spin up a webserver listening for requests on `localhost` port `8080`

## API

All requests will return the current state of the associated game if the request is accepted:

```json
{
    "id": "some-game-id",
    "status": ""
    "player1": "",
    "player2": "",
    "nextPlayerToMove": "",
    "winner": ""
}
```

### `GET /api/games/{id}`

Returns the current state of a given game with additional attributes (which do not spoil opponent moves):

```json
{
    "id": "some-game-id",
    "status": ""
    "player1": "",
    "player2": "",
    "nextPlayerToMove": "",
    "winner": ""
}
```

### `POST /api/games`

Creates a new game. Enter player name in the request-body:

```json
{
  "name": "Lisa"
}
```

### `POST /api/games/{id}/join`

Connects to a game with a given ID. Enter player name in the request-body:

```json
{
  "name": "Pelle"
}
```

### `POST /api/games/{id}/move`

Make a move. Enter name and move in the request-body:

```json
{
  "name": "Lisa",
  "move": "Rock"
}
```

## Notes

- No database is used, states for games are kept in memory (no persitance)
- Possible future improvements: stricter validation, more detailed error responses, custom names for move options, persistent repositories
- Generative AI was not used to avoid overcomplicate project structure etc. GPT-5.4 Mini was however to try out how good it was at noticing vulnerabilities in the API. The findings of this was used when implementing the concurrent repository and games limiting.
